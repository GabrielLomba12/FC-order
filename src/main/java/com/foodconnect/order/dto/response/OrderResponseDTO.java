package com.foodconnect.order.dto.response;

import com.foodconnect.order.model.OrderStatus;

import java.time.LocalDateTime;
import java.util.List;

public class OrderResponseDTO {

    private Long orderId;
    private Long customerId;
    private LocalDateTime orderDate;
    private String paymentType;
    private String orderStatus;
    private List<ItemResponseDTO> items;

    public OrderResponseDTO(){}

    public OrderResponseDTO(Long orderId, Long customerId, LocalDateTime orderDate,
                            String paymentType, OrderStatus orderStatus) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.orderDate = orderDate;
        this.paymentType = paymentType;
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

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public List<ItemResponseDTO> getItems() {
        return items;
    }

    public void setItems(List<ItemResponseDTO> items) {
        this.items = items;
    }
}
