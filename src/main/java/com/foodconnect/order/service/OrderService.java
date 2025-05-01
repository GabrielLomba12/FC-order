package com.foodconnect.order.service;

import com.foodconnect.order.dto.NotificationDTO;
import com.foodconnect.order.dto.response.OrderResponseDTO;
import com.foodconnect.order.model.OrderModel;
import com.foodconnect.order.model.OrderStatus;
import com.foodconnect.order.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class OrderService {
    @Autowired
    private NotificationService notificationService;

    @Autowired
    private OrderRepository orderRepository;

    public void updateOrderStatus(Long orderId, OrderStatus status) {
        OrderModel order = orderRepository.findById(orderId).orElseThrow();
        order.setOrderStatus(status);
        orderRepository.save(order);

        if (status == OrderStatus.PAID || status == OrderStatus.READY) {
            NotificationDTO dto = new NotificationDTO();
            dto.setCustomerId(order.getCustomerId());
            dto.setStatus(status);

            notificationService.save(dto); // envia a notificação internamente
        }
    }

    //TODO: Include product name in object return.

    public ResponseEntity<Map<String, Object>> listOrdersByStore(Long storeId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id"));
        Page<OrderResponseDTO> orderPage = orderRepository.findOrderDTOsByStoreId(storeId, pageable);

        Map<String, Object> response = new HashMap<>();
        response.put("orders", orderPage.getContent());
        response.put("totalPages", orderPage.getTotalPages());
        response.put("totalElements", orderPage.getTotalElements());
        response.put("currentPage", orderPage.getNumber());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
