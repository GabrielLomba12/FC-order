package com.foodconnect.order.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "`order`")
public class OrderModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "fk_customer_id")
    private CustomerModel customerId;

    private LocalDateTime orderDate;
    private String paymentType;

    @OneToMany(mappedBy = "id.orderId", cascade = CascadeType.ALL)
    private List<ItemOrderModel> items;

    @OneToMany(mappedBy = "orderModel", cascade = CascadeType.ALL)
    private List<OrderStatusHistoryModel> statusList;

    public Long getId() {
        return id;
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
}
