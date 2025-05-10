package com.foodconnect.order.controller;

import com.foodconnect.order.dto.NotificationDTO;
import com.foodconnect.order.model.NotificationModel;
import com.foodconnect.order.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/notifications")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @PostMapping
    public ResponseEntity<Void> notify(@RequestBody NotificationDTO dto) {
        notificationService.save(dto);
        return ResponseEntity.ok().build();
    }

//    @GetMapping("/customer/{id}")
//    public ResponseEntity<List<NotificationModel>> getNotifications(@PathVariable Long id) {
//        return ResponseEntity.ok(notificationService.getByCustomerId(id));
//    }
}
