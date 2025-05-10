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

//    @Query("SELECT new com.foodconnect.order.dto.response.OrderResponseDTO(" +
//            "o.id, o.customerId, o.orderDate, o.paymentType, o.orderStatus) " +
//            "FROM OrderModel o " +
//            "JOIN ItemOrderModel io ON io.order = o " +
//            "JOIN ProductModel p ON io.product = p " +
//            "WHERE p.storeId = :storeId")
//    Page<OrderResponseDTO> findOrderDTOsByStoreId(@Param("storeId") Long storeId, Pageable pageable);

//    @Query("SELECT o FROM OrderModel o " +
//            "JOIN FETCH o.items io " +
//            "JOIN FETCH io.product p " +
//            "WHERE p.storeId = :storeId")
//    Page<OrderResponseDTO> findOrdersWithItemsByStoreId(@Param("storeId") Long storeId, Pageable pageable);

    @Query(
            value = """
        SELECT 
            o.id AS orderId,
            o.customer_id AS customerId,
            o.order_date AS orderDate,
            o.payment_type AS paymentType,
            o.order_status AS orderStatus,
            p.name AS productName,
            p.price AS unitPrice,
            io.quantity AS quantity
        FROM `order` o
        JOIN item_order io ON io.order_id = o.id
        JOIN product p ON io.product_id = p.id
        WHERE p.store_id = :storeId
        ORDER BY o.id
        """,
            countQuery = """
        SELECT COUNT(*) 
        FROM `order` o
        JOIN item_order io ON io.order_id = o.id
        JOIN product p ON io.product_id = p.id
        WHERE p.store_id = :storeId
        """,
            nativeQuery = true
    )
    Page<Object[]> findFlatOrderItemsByStoreId(@Param("storeId") Long storeId, Pageable pageable);



}
