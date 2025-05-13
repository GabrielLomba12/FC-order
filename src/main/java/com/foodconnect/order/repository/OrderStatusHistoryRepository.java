package com.foodconnect.order.repository;

import com.foodconnect.order.model.OrderModel;
import com.foodconnect.order.model.OrderStatusHistoryModel;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderStatusHistoryRepository extends JpaRepository<OrderStatusHistoryModel, Long> {
    List<OrderStatusHistoryModel> findByOrderModel(OrderModel orderId, Sort sort);
}
