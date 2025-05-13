package com.foodconnect.order.service;

import com.foodconnect.order.controller.OrderSseController;
import com.foodconnect.order.dto.ErrorResponseDTO;
import com.foodconnect.order.dto.StoreDTO;
import com.foodconnect.order.dto.UpdateOrderStatusDTO;
import com.foodconnect.order.dto.ValidateCodeDTO;
import com.foodconnect.order.dto.request.CartInfoRequestDTO;
import com.foodconnect.order.dto.request.RegisterOrderRequestDTO;
import com.foodconnect.order.dto.response.*;
import com.foodconnect.order.dto.response.UserOrderDTO;
import com.foodconnect.order.enums.OrderStatus;
import com.foodconnect.order.model.CustomerModel;
import com.foodconnect.order.model.EmployeeModel;
import com.foodconnect.order.model.ItemOrderModel;
import com.foodconnect.order.model.OrderModel;
import com.foodconnect.order.model.OrderStatusHistoryModel;
import com.foodconnect.order.model.ProductModel;
import com.foodconnect.order.model.StoreModel;
import com.foodconnect.order.model.key.ItemOrderKey;
import com.foodconnect.order.repository.CustomerRepository;
import com.foodconnect.order.repository.EmployeeRepository;
import com.foodconnect.order.repository.ItemOrderRepository;
import com.foodconnect.order.repository.OrderRepository;
import com.foodconnect.order.repository.OrderStatusHistoryRepository;
import com.foodconnect.order.repository.ProductRepository;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class OrderService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ItemOrderRepository itemOrderRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private OrderStatusHistoryRepository orderStatusHistoryRepository;

    @Autowired
    private OrderSseController sseController;

    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    public ResponseEntity<UpdateOrderStatusDTO> updateOrderStatus(UpdateOrderStatusDTO dto) {

        try {
            OrderModel order = orderRepository.findById(dto.getOrderId())
                    .orElseThrow(() -> new IllegalArgumentException("Pedido não encontrado"));

            EmployeeModel employee = employeeRepository.findById(dto.getEmployeeId())
                    .orElseThrow(() -> new IllegalArgumentException("Funcionário não encontrado"));

            OrderStatusHistoryModel orderStatusHistoryModel = new OrderStatusHistoryModel(dto);
            orderStatusHistoryModel.setOrderModel(order);
            orderStatusHistoryModel.setEmployeeModel(employee);

            ZonedDateTime zonedDateTime = ZonedDateTime.now(ZoneId.of("America/Sao_Paulo"));
            Date date = Date.from(zonedDateTime.toInstant());
            orderStatusHistoryModel.setUpdatedAt(date);

            orderStatusHistoryRepository.save(orderStatusHistoryModel);

            if (orderStatusHistoryModel.getOrderStatus() == OrderStatus.AVAILABLE) {
                scheduleOrderTimeoutCheck(order.getId(), 15);
            }

            sseController.notifyUpdateOrder(order.getId(), orderStatusHistoryModel.getOrderStatus());

            return ResponseEntity.ok(dto);

        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    public ResponseEntity<?> validationCodeAndUpdateOrder(ValidateCodeDTO dto) {
        try {
            OrderModel order = orderRepository.findById(dto.getOrderId())
                    .orElseThrow(() -> new IllegalArgumentException("Pedido não encontrado"));

            String phone = order.getCustomerId().getPhoneNumber();
            String expectedCode = phone.substring(phone.length() - 4);

            if (!expectedCode.equals(dto.getCode())) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Código de retirada inválido.");
            }

            UpdateOrderStatusDTO updateDTO = new UpdateOrderStatusDTO();
            updateDTO.setOrderId(dto.getOrderId());
            updateDTO.setEmployeeId(dto.getEmployeeId());
            updateDTO.setOrderStatus(dto.getOrderStatus());

            return updateOrderStatus(updateDTO);

        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    public ResponseEntity<Page<OrderModel>> ordersByStore(Long storeId, int page, int size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id"));
        Page<OrderModel> order = orderRepository.findOrdersByStoreId(storeId, pageable);

        return ResponseEntity.ok(order);
    }

    public ResponseEntity<OrderDetailsDTO> getOrderDetails(Long orderId) {

        OrderModel order = orderRepository.findById(orderId).orElseThrow();

        OrderHistoryDTO dto = new OrderHistoryDTO();
        List<ProductDTO> productDTOList = new ArrayList<>();
        OrderDetailsDTO response = new OrderDetailsDTO();

        order.getStatusList()
                .forEach(history -> {
                    Date updatedAt = history.getUpdatedAt();
                    switch (history.getOrderStatus()) {
                        case PAID -> dto.setPaymentTime(updatedAt);
                        case PREPARING -> dto.setPreparingTime(updatedAt);
                        case AVAILABLE -> dto.setAvailableTime(updatedAt);
                        case FINISHED -> dto.setFinishedTime(updatedAt);
                    }
                });

        order.getItems().forEach(item -> {
            ProductDTO productDTO = new ProductDTO(item.getId().getProductId(), item.getQuantity());
            productDTOList.add(productDTO);
        });

        response.setOrderHistory(dto);
        response.setProducts(productDTOList);

        return ResponseEntity.ok(response);
    }

    public ResponseEntity<List<UserOrderDTO>> listOrdersByUser(Long userId, int page, int size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "orderDate"));
        Page<OrderModel> orders = orderRepository.findOrdersByUserId(userId, pageable);

        List<UserOrderDTO> responseList = new ArrayList<>();
        for (OrderModel orderModel : orders) {
            double totalPriceOrder = 0;
            UserOrderDTO userOrderDTO = new UserOrderDTO();
            for (ItemOrderModel itemOrderModel : orderModel.getItems()) {
                double priceItemQuantity = itemOrderModel.getQuantity() * itemOrderModel.getUnitPrice();
                totalPriceOrder += priceItemQuantity;
            }
            totalPriceOrder = Math.round(totalPriceOrder * 100.0) / 100.0;
            List<OrderStatusHistoryModel> listStatus = orderStatusHistoryRepository.findByOrderModel(orderModel, Sort.by(Sort.Direction.DESC, "updatedAt"));
            userOrderDTO.setOrderStatus(listStatus.getFirst().getOrderStatus().toString());
            userOrderDTO.setTotalPrice(totalPriceOrder);
            userOrderDTO.setOrderId(orderModel.getId());
            userOrderDTO.setOrderDate(orderModel.getOrderDate());
            responseList.add(userOrderDTO);
        }

        return ResponseEntity.ok(responseList);
    }

    public ResponseEntity<?> registerOrder(RegisterOrderRequestDTO order) {
        CustomerModel customer = customerRepository.findById(order.getCustomerId()).orElse(null);

        if (order.getCartInfo() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponseDTO("Não é possível registrar o pedido sem os itens!"));
        }
        if (order.getCartInfo().getFirst() == null && customer == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponseDTO("Não é possível registrar o pedido sem os itens e um cliente existente!"));
        } else if (order.getCartInfo().getFirst() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponseDTO("Não é possível registrar o pedido sem os itens!"));
        } else if (customer == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponseDTO("O cliente informado no pedido não foi encontrado, tente novamente com outro cliente."));
        }

        OrderModel orderModel = new OrderModel();
        orderModel.setCustomerId(customer);
        orderModel.setOrderDate(Date.from(ZonedDateTime.now(ZoneId.of("America/Sao_Paulo")).toInstant()));
        orderModel.setPaymentType(order.getPaymentType());
        OrderModel orderSaved = orderRepository.save(orderModel);

        AtomicReference<StoreModel> storeRef = new AtomicReference<>();

        order.getCartInfo().forEach(cartInfo -> {
            ProductModel productModel = productRepository.findById(cartInfo.getProductId())
                    .orElseThrow(() -> new RuntimeException("Produto não encontrado"));
            storeRef.set(productModel.getStore());
            registerItemOrder(orderSaved, productModel, cartInfo);
            productModel.setStock(productModel.getStock() - cartInfo.getQuantity());
            productRepository.save(productModel);
        });

        StoreModel store = storeRef.get();

        OrderStatusHistoryModel orderStatusHistoryModel = new OrderStatusHistoryModel();
        orderStatusHistoryModel.setOrderModel(orderSaved);
        orderStatusHistoryModel.setOrderStatus(OrderStatus.PAID);
        orderStatusHistoryModel.setUpdatedAt(Date.from(ZonedDateTime.now(ZoneId.of("America/Sao_Paulo")).toInstant()));
        orderStatusHistoryRepository.save(orderStatusHistoryModel);

        RegisterOrderResponseDTO response = new RegisterOrderResponseDTO();
        response.setOrderId(orderSaved.getId());
        response.setStatus(OrderStatus.PAID);
        response.setStore(new StoreDTO(store));
        String lastFourDigits = customer.getPhoneNumber().substring(customer.getPhoneNumber().length() - 4);
        response.setWithdrawalCode(lastFourDigits);
        response.setAvailabilityForecast(predictAvailability(order.getExpectedDeliveryTime()));

        return ResponseEntity.ok(response);
    }

    public String predictAvailability(String expectedDeliveryTime) {
        if (expectedDeliveryTime != null && expectedDeliveryTime.matches(".*\\d+.*")) { // Verifica se contém números
            String numericPart = expectedDeliveryTime.replaceAll("\\D+", ""); // Extrai apenas os números
            int minutesToAdd = Integer.parseInt(numericPart);
            ZonedDateTime currentDateTime = ZonedDateTime.now(ZoneId.of("America/Sao_Paulo"));
            ZonedDateTime updatedDateTime = currentDateTime.plusMinutes(minutesToAdd);
            return updatedDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        } else {
            return "Pronta entrega";
        }
    }

    private void registerItemOrder(OrderModel orderModel, ProductModel productModel, CartInfoRequestDTO cartInfo) {
        ItemOrderModel itemPedidoModel = new ItemOrderModel(cartInfo);
        itemPedidoModel.setId(new ItemOrderKey(orderModel, productModel));
        itemOrderRepository.save(itemPedidoModel);
    }

    private void scheduleOrderTimeoutCheck(Long orderId, int delayMinutes) {
        scheduler.schedule(() -> {
            OrderModel order = orderRepository.findById(orderId).orElse(null);
            if (order != null) {
                List<OrderStatusHistoryModel> listStatus = orderStatusHistoryRepository.findByOrderModel(order, Sort.by(Sort.Direction.DESC, "updatedAt"));

                if (listStatus != null) {
                    for (OrderStatusHistoryModel status : listStatus) {
                        if (status.getOrderStatus() == OrderStatus.FINISHED) {
                            return;
                        }
                    }
                }

                // Repor estoque
                List<ItemOrderModel> itens = itemOrderRepository.findByIdOrderId(order);
                for (ItemOrderModel item : itens) {
                    ProductModel product = item.getId().getProductId();
                    product.setStock(product.getStock() + item.getQuantity());
                    productRepository.save(product);
                }

                // Salvar histórico de status
                OrderStatusHistoryModel cancelHistory = new OrderStatusHistoryModel();
                cancelHistory.setOrderModel(order);
                cancelHistory.setOrderStatus(OrderStatus.CANCELED);
                cancelHistory.setUpdatedAt(Date.from(ZonedDateTime.now(ZoneId.of("America/Sao_Paulo")).toInstant()));
                orderStatusHistoryRepository.save(cancelHistory);
            }
        }, delayMinutes, TimeUnit.MINUTES);
    }
}
