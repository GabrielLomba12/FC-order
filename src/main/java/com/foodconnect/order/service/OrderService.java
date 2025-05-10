package com.foodconnect.order.service;

import com.foodconnect.order.dto.NotificationDTO;
import com.foodconnect.order.dto.response.ItemResponseDTO;
import com.foodconnect.order.dto.response.OrderResponseDTO;
import com.foodconnect.order.model.OrderModel;
import com.foodconnect.order.model.OrderStatus;
import com.foodconnect.order.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.*;

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

    public Page<OrderResponseDTO> getPagedGroupedOrders(Long storeId, Pageable pageable) {
        Page<Object[]> rows = orderRepository.findFlatOrderItemsByStoreId(storeId, pageable);
        Map<Long, OrderResponseDTO> orderMap = new LinkedHashMap<>();

        for (Object[] row : rows) {
            Long orderId = ((Number) row[0]).longValue();

            OrderResponseDTO order = orderMap.get(orderId);
            if (order == null) {
                order = new OrderResponseDTO(
                        orderId,
                        ((Number) row[1]).longValue(),
                        ((Timestamp) row[2]).toLocalDateTime(),
                (String) row[3],
                        OrderStatus.valueOf((String) row[4])
                );
                order.setItems(new ArrayList<>());
                orderMap.put(orderId, order);
            }

            ItemResponseDTO item = new ItemResponseDTO(
                    (String) row[5],
                    ((Number) row[6]).doubleValue(),
                    ((Number) row[7]).intValue()
            );

            order.getItems().add(item);
        }

        List<OrderResponseDTO> dtoList = new ArrayList<>(orderMap.values());
        return new PageImpl<>(dtoList, pageable, rows.getTotalElements());
    }

}
