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
    List<OrderModel> findByCustomerId(Long customerId);

    @Query("SELECT new com.foodconnect.order.dto.response.OrderResponseDTO(" +
            "o.id, o.customerId, o.orderDate, o.paymentType, o.orderStatus) " +
            "FROM OrderModel o " +
            "JOIN ItemOrderModel io ON io.order = o " +
            "JOIN ProductModel p ON io.product = p " +
            "WHERE p.storeId = :storeId")
    Page<OrderResponseDTO> findOrderDTOsByStoreId(@Param("storeId") Long storeId, Pageable pageable);
}
