package com.foodconnect.order.dto.response;

import com.foodconnect.order.dto.StoreDTO;
import com.foodconnect.order.enums.OrderStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

public class RegisterOrderResponseDTO {
    private Long orderId;
    private String availabilityForecast;
    private String withdrawalCode;
    private StoreDTO store;
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    public RegisterOrderResponseDTO() {
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getAvailabilityForecast() {
        return availabilityForecast;
    }

    public void setAvailabilityForecast(String availabilityForecast) {
        this.availabilityForecast = availabilityForecast;
    }

    public String getWithdrawalCode() {
        return withdrawalCode;
    }

    public void setWithdrawalCode(String withdrawalCode) {
        this.withdrawalCode = withdrawalCode;
    }

    public StoreDTO getStore() {
        return store;
    }

    public void setStore(StoreDTO store) {
        this.store = store;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }
}
