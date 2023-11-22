package com.example.NotificationService.controller;
import com.example.NotificationService.model.Notification;
import com.example.NotificationService.model.NotificationRecord;
import com.example.NotificationService.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @PostMapping("/notify")
    @ResponseBody
    public ResponseEntity requestToNotify(@RequestParam String userID){
        System.out.println("Ride join");
        Notification notification = new Notification();
        Notification subnotification = new Notification();
        subnotification.setMessage("test");
        subnotification.setPassengerID("test");
        notification.setMessage(subnotification);
        notification.setPassengerID("test passenger");
        NotificationRecord notificationRecord = notificationService.notifyUser(userID, notification);
        return ResponseEntity.ok(notificationRecord);
    }

    @GetMapping("/notifications")
    @ResponseBody
    public ResponseEntity getNotifications(@RequestParam String userID) {
        List<NotificationRecord> notifications = notificationService.getNotificationsForUser(userID);
        return ResponseEntity.ok(notifications);
    }

    @DeleteMapping("/notifications/{notifiactionId}")
    @ResponseBody
    public ResponseEntity deleteNotificationRecord(@PathVariable String notifiactionId) {
        notificationService.deleteNotification(notifiactionId);
        return ResponseEntity.ok("Notification record successfully deleted.");
    }
}
