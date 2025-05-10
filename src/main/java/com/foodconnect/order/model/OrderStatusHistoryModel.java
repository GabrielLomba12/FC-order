package com.foodconnect.order.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.foodconnect.order.dto.UpdateOrderStatusDTO;
import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "order_status_history")
public class OrderStatusHistoryModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    private Date updatedAt;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "order_id")
    private OrderModel orderModel;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "changed_by_employee")
    private EmployeeModel employeeModel;

    public OrderStatusHistoryModel(){}

    public OrderStatusHistoryModel(UpdateOrderStatusDTO dto) {
        this.orderStatus = dto.getOrderStatus();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public OrderModel getOrderModel() {
        return orderModel;
    }

    public void setOrderModel(OrderModel orderModel) {
        this.orderModel = orderModel;
    }

    public EmployeeModel getEmployeeModel() {
        return employeeModel;
    }

    public void setEmployeeModel(EmployeeModel employeeModel) {
        this.employeeModel = employeeModel;
    }
}
