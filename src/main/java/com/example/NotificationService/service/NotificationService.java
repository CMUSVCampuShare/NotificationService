package com.example.NotificationService.service;

import com.example.NotificationService.model.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    public void notifyUser(String userID, Notification notification){
        messagingTemplate.convertAndSendToUser(userID, "/notification", notification);
    }
}
