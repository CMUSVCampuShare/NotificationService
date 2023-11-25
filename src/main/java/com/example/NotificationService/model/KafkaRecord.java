package com.example.NotificationService.model;

import lombok.Data;

@Data
public class KafkaRecord {
    private String message;
    private String status;
    private String recipientUserID;
    private Object joinNotification;
}
