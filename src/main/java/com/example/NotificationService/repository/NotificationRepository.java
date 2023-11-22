package com.example.NotificationService.repository;

import com.example.NotificationService.model.NotificationRecord;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface NotificationRepository extends MongoRepository<NotificationRecord,String> {
    List<NotificationRecord> findByRecipientId(String recipientId);
    NotificationRecord deleteByNotificationId(String notificationId);
}
