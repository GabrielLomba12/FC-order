package com.foodconnect.order.service;

import com.foodconnect.order.dto.UpdateOrderStatusDTO;
import com.foodconnect.order.dto.response.OrderDetailsDTO;
import com.foodconnect.order.dto.response.OrderHistoryDTO;
import com.foodconnect.order.dto.response.ProductDTO;
import com.foodconnect.order.model.EmployeeModel;
import com.foodconnect.order.model.OrderModel;
import com.foodconnect.order.model.OrderStatusHistoryModel;
import com.foodconnect.order.repository.EmployeeRepository;
import com.foodconnect.order.repository.OrderRepository;
import com.foodconnect.order.repository.OrderStatusHistoryRepository;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private OrderStatusHistoryRepository orderStatusHistoryRepository;

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

            return ResponseEntity.ok(dto);

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
}
