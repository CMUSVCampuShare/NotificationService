package com.example.NotificationService.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "notifications")
@Data
public class NotificationRecord {
    @Id
    private String notificationId;
    private String recipientId;
    private Notification notification;
}
