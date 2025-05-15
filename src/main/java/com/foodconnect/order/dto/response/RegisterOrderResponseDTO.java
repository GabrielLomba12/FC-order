package com.foodconnect.order.dto.response;

public class RegisterOrderResponseDTO {
    private Long orderId;

    public RegisterOrderResponseDTO() {
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }
}
