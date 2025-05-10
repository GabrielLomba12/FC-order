package com.foodconnect.order.dto.response;

import com.foodconnect.order.model.OrderModel;

import java.util.Date;

public class OrderHistoryDTO {

    private Date paymentTime;
    private Date preparingTime;
    private Date availableTime;
    private Date finishedTime;

    public Date getPaymentTime() {
        return paymentTime;
    }

    public void setPaymentTime(Date paymentTime) {
        this.paymentTime = paymentTime;
    }

    public Date getPreparingTime() {
        return preparingTime;
    }

    public void setPreparingTime(Date preparingTime) {
        this.preparingTime = preparingTime;
    }

    public Date getAvailableTime() {
        return availableTime;
    }

    public void setAvailableTime(Date availableTime) {
        this.availableTime = availableTime;
    }

    public Date getFinishedTime() {
        return finishedTime;
    }

    public void setFinishedTime(Date finishedTime) {
        this.finishedTime = finishedTime;
    }
}
