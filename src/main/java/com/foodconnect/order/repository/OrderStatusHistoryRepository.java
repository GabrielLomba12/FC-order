package com.foodconnect.order.repository;

import com.foodconnect.order.model.OrderStatusHistoryModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderStatusHistoryRepository extends JpaRepository<OrderStatusHistoryModel, Long> {
}
