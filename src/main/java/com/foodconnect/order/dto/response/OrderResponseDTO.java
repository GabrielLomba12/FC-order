package com.foodconnect.order.dto.response;

import com.foodconnect.order.model.OrderModel;
import com.foodconnect.order.model.OrderStatus;

import java.time.LocalDateTime;

public class OrderResponseDTO {

    private Long id;
    private Long customerId;
    private LocalDateTime orderDate;
    private String paymentType;
    private OrderStatus orderStatus;

    public OrderResponseDTO(){}

    public OrderResponseDTO(Long id, Long customerId, LocalDateTime orderDate, String paymentType, OrderStatus orderStatus) {
        this.id = id;
        this.customerId = customerId;
        this.orderDate = orderDate;
        this.paymentType = paymentType;
        this.orderStatus = orderStatus;
    }

    public Long getId() {
        return id;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }
}
