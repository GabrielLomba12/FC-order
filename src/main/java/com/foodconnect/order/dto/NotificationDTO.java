package com.foodconnect.order.dto;

import com.foodconnect.order.model.OrderStatus;
import org.hibernate.query.Order;

public class NotificationDTO {
    private Long customerId;
    private OrderStatus status;

    public NotificationDTO(Long customerId, OrderStatus status) {
        this.customerId = customerId;
        this.status = status;
    }

    public NotificationDTO() {}

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }
}
