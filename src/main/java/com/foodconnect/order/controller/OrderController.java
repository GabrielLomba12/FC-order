package com.foodconnect.order.controller;

import com.foodconnect.order.dto.UpdateOrderStatusDTO;
import com.foodconnect.order.dto.ValidateCodeDTO;
import com.foodconnect.order.dto.request.RegisterOrderRequestDTO;
import com.foodconnect.order.dto.response.OrderDetailsDTO;
import com.foodconnect.order.dto.response.UserOrderDTO;
import com.foodconnect.order.model.OrderModel;
import com.foodconnect.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/list")
    public ResponseEntity<Page<OrderModel>> listOrdersByStore(@RequestParam Long storeId,
                                                              @RequestParam(defaultValue = "0") int page,
                                                              @RequestParam(defaultValue = "10") int size) {
        return orderService.ordersByStore(storeId, page, size);
    }

    @PutMapping("/updstatus")
    public ResponseEntity<UpdateOrderStatusDTO> updateOrderStatus(@RequestBody UpdateOrderStatusDTO dto) {
        return orderService.updateOrderStatus(dto);
    }

    @GetMapping("/details")
    public ResponseEntity<OrderDetailsDTO> getOrderDetails(@RequestParam Long orderId) {
        return orderService.getOrderDetails(orderId);
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerOrder(@RequestBody RegisterOrderRequestDTO order) {
        return orderService.registerOrder(order);
    }

    @GetMapping("/user-orders")
    public ResponseEntity<List<UserOrderDTO>> listOrdersByUser(@RequestParam Long userId) {
        return orderService.listOrdersByUser(userId);
    }

    @PutMapping("/validate-code")
    public ResponseEntity<?> validateOrderCode (@RequestBody ValidateCodeDTO dto) {
        return orderService.validationCodeAndUpdateOrder(dto);
    }
}
