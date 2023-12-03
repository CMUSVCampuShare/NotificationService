package com.example.NotificationService.model;

import lombok.Data;

@Data
public class NotificationDetails {
    private String passengerID;
    private String postId;
    private String postTitle;
    private Object notificationBody;
}
