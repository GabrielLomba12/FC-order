package com.foodconnect.order.controller;

import com.foodconnect.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@CrossOrigin("*")
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/list")
    public ResponseEntity<Map<String, Object>> listOrdersByStore(
            @RequestParam Long storeId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
            ) {
        return orderService.listOrdersByStore(storeId, page, size);
    }
}
