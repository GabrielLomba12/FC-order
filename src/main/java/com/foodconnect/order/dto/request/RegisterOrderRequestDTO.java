package com.foodconnect.order.dto.request;

import java.util.List;

public class RegisterOrderRequestDTO {
    private Long customerId;
    private String paymentType;
    private String expectedDeliveryTime;
    List<CartInfoRequestDTO> cartInfo;

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public List<CartInfoRequestDTO> getCartInfo() {
        return cartInfo;
    }

    public void setCartInfo(List<CartInfoRequestDTO> cartInfo) {
        this.cartInfo = cartInfo;
    }

    public String getExpectedDeliveryTime() {
        return expectedDeliveryTime;
    }

    public void setExpectedDeliveryTime(String expectedDeliveryTime) {
        this.expectedDeliveryTime = expectedDeliveryTime;
    }
}
