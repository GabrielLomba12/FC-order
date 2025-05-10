package com.foodconnect.order.repository;

import com.foodconnect.order.dto.response.OrderResponseDTO;
import com.foodconnect.order.model.OrderModel;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OrderRepository extends JpaRepository<OrderModel, Long> {
//    List<OrderModel> findByCustomerId(Long customerId);

    @Query("SELECT DISTINCT o FROM OrderModel o JOIN o.items i WHERE i.id.productId.store = :storeId")
    Page<OrderModel> findOrdersByStoreId(@Param("storeId") Long storeId, Pageable pageable);
}
