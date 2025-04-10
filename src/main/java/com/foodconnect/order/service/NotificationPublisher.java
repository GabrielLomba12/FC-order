package com.foodconnect.order.service;

import com.foodconnect.order.dto.NotificationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class NotificationPublisher {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public void publish(NotificationDTO notification) {
        redisTemplate.convertAndSend("notifications", notification);
    }
}
