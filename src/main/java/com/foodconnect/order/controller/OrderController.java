package com.foodconnect.order.controller;

import com.foodconnect.order.dto.response.OrderResponseDTO;
import com.foodconnect.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/list")
    public ResponseEntity<Page<OrderResponseDTO>> listOrdersByStore(
            @RequestParam Long storeId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
            ) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(orderService.getPagedGroupedOrders(storeId, pageable));
    }
}
