package com.foodconnect.order.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.foodconnect.order.dto.NotificationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;

public class NotificationSubscriber {
    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public void onMessage(Object message, byte[] pattern) {
        try {
            NotificationDTO notification = objectMapper.readValue(message.toString(), NotificationDTO.class);
            messagingTemplate.convertAndSend("/topic/notifications/" + notification.getCustomerId(), notification);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
