package com.foodconnect.order.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "`order`")
public class OrderModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "fk_customer_id")
    private CustomerModel customerId;

    private Date orderDate;
    private String paymentType;
    private String withdrawalCode;
    private String availabilityForecast;

    @OneToMany(mappedBy = "id.orderId", cascade = CascadeType.ALL)
    private List<ItemOrderModel> items;

    @OneToMany(mappedBy = "orderModel", cascade = CascadeType.ALL)
    private List<OrderStatusHistoryModel> statusList;

    public Long getId() {
        return id;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public CustomerModel getCustomerId() {
        return customerId;
    }

    public void setCustomerId(CustomerModel customerId) {
        this.customerId = customerId;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<ItemOrderModel> getItems() {
        return items;
    }

    public void setItems(List<ItemOrderModel> items) {
        this.items = items;
    }

    public List<OrderStatusHistoryModel> getStatusList() {
        return statusList;
    }

    public void setStatusList(List<OrderStatusHistoryModel> statusList) {
        this.statusList = statusList;
    }

    public String getWithdrawalCode() {
        return withdrawalCode;
    }

    public void setWithdrawalCode(String withdrawalCode) {
        this.withdrawalCode = withdrawalCode;
    }

    public String getAvailabilityForecast() {
        return availabilityForecast;
    }

    public void setAvailabilityForecast(String availabilityForecast) {
        this.availabilityForecast = availabilityForecast;
    }
}
