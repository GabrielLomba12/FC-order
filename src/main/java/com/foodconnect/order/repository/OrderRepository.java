package com.foodconnect.order.repository;

import com.foodconnect.order.dto.response.OrderResponseDTO;
import com.foodconnect.order.enums.OrderStatus;
import com.foodconnect.order.model.OrderModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OrderRepository extends JpaRepository<OrderModel, Long> {

    @Query("SELECT DISTINCT o FROM OrderModel o JOIN o.items i JOIN i.id.productId p WHERE p.store.id = :storeId")
    Page<OrderModel> findOrdersByStoreId(@Param("storeId") Long storeId, Pageable pageable);

    @Query("SELECT o FROM OrderModel o WHERE o.customerId.id = :userId")
    Page<OrderModel> findOrdersByUserId(@Param("userId") Long userId, Pageable pageable);

    @Query("""
    SELECT DISTINCT o
    FROM OrderModel o
    JOIN o.items i
    JOIN i.id.productId p
    JOIN o.statusList s
    WHERE p.store.id = :storeId
    AND s.updatedAt = (
        SELECT MAX(s2.updatedAt)
        FROM OrderStatusHistoryModel s2
        WHERE s2.orderModel.id = o.id
    )
    AND s.orderStatus NOT IN :statuses
    """)
    Page<OrderModel> findByStoreIdAndLastStatusNotIn(
            @Param("storeId") Long storeId,
            @Param("statuses") List<OrderStatus> statuses,
            Pageable pageable
    );

}


