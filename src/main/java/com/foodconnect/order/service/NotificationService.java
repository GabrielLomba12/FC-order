package com.foodconnect.order.service;

import com.foodconnect.order.dto.NotificationDTO;
import com.foodconnect.order.model.NotificationModel;
import com.foodconnect.order.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    public void save(NotificationDTO dto) {
        NotificationModel notification = new NotificationModel();
        notification.setCustomerId(dto.getCustomerId());
        notification.setStatus(dto.getStatus());
        notification.setTimestamp(LocalDateTime.now());
        notificationRepository.save(notification);
    }

    public List<NotificationModel> getByCustomerId(Long customerId) {
        return notificationRepository.findByCustomerId(customerId);
    }
}
