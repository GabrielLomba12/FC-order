package com.foodconnect.order.repository;

import com.foodconnect.order.model.ItemOrderModel;
import com.foodconnect.order.model.OrderModel;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ItemOrderRepository extends JpaRepository<ItemOrderModel, Long> {
}
