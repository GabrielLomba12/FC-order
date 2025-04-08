package com.foodconnect.order.repository;

import com.foodconnect.order.model.NotificationModel;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface NotificationRepository extends JpaRepository<NotificationModel, Long> {
    List<NotificationModel> findByCustomerId(Long customerId);
}
