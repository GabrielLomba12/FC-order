package com.foodconnect.order.repository;

import com.foodconnect.order.model.ItemOrderModel;
import com.foodconnect.order.model.OrderModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemOrderRepository extends JpaRepository<ItemOrderModel, Long> {

    List<ItemOrderModel> findByIdOrderId(OrderModel productId);
}
