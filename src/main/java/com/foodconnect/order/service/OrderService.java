package com.foodconnect.order.service;

import com.foodconnect.order.dto.NotificationDTO;
import com.foodconnect.order.model.OrderModel;
import com.foodconnect.order.model.OrderStatus;
import com.foodconnect.order.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;

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
}
