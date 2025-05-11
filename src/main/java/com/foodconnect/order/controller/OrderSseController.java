package com.foodconnect.order.controller;

import com.foodconnect.order.enums.OrderStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@CrossOrigin("*")
@RestController
@RequestMapping("/sse")
public class OrderSseController {
    private final Map<Long, SseEmitter> emitters = new ConcurrentHashMap<>();

    @GetMapping("/order/{orderId}")
    public SseEmitter streamOrder(@PathVariable Long orderId) {
        SseEmitter emitter = new SseEmitter(0L); // sem timeout
        emitters.put(orderId, emitter);

        emitter.onCompletion(() -> emitters.remove(orderId));
        emitter.onTimeout(() -> emitters.remove(orderId));

        return emitter;
    }

    public void notifyUpdateOrder(Long orderId, OrderStatus newStatus) {
        SseEmitter emitter = emitters.get(orderId);
        if (emitter != null) {
            try {
                emitter.send(SseEmitter.event()
                        .name("status-updated")
                        .data(newStatus.name()));
            } catch (IOException e) {
                emitter.completeWithError(e);
                emitters.remove(orderId);
            }
        }
    }
}
