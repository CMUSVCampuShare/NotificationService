package com.example.NotificationService.service;

import com.example.NotificationService.model.NotificationRecord;
import com.example.NotificationService.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class NotificationService {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private NotificationRepository notificationRepository;

    public NotificationRecord notifyUser(String userID, Object notification){
        NotificationRecord notificationRecord = new NotificationRecord();
        notificationRecord.setRecipientId(userID);
        notificationRecord.setNotification(notification);
        notificationRecord.setNotificationId(UUID.randomUUID().toString());
        NotificationRecord savedRecord = notificationRepository.save(notificationRecord);
        System.out.println("Saved record: " + savedRecord);

        messagingTemplate.convertAndSendToUser(userID, "/notification", notificationRecord);
        return savedRecord;
    }

    public List<NotificationRecord> getNotificationsForUser(String userID){
        List<NotificationRecord> notificationRecords = notificationRepository.findByRecipientId(userID);
        return notificationRecords;
    }

    public void deleteNotification(String notificationId){
        NotificationRecord deletedRecord = notificationRepository.deleteByNotificationId(notificationId);
        System.out.println("Deleted record: " + deletedRecord);
    }
}
