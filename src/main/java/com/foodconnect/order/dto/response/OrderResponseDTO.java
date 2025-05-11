package com.foodconnect.order.dto.response;

import com.foodconnect.order.enums.OrderStatus;

import java.time.LocalDateTime;

public class OrderResponseDTO {

    private Long orderId;
    private Long customerId;
    private LocalDateTime orderDate;
    private String orderStatus;

    public OrderResponseDTO(){}

    public OrderResponseDTO(Long orderId, Long customerId, LocalDateTime orderDate,
                            OrderStatus orderStatus) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.orderDate = orderDate;
        this.orderStatus = orderStatus.name();
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }
}
