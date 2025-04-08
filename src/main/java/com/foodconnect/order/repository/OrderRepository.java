package com.foodconnect.order.repository;

import com.foodconnect.order.model.OrderModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<OrderModel, Long> {
    List<OrderModel> findByCustomerId(Long customerId);
}
