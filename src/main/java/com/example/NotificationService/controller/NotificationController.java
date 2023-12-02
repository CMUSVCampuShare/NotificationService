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
        String notification = "Sorry AB your ride got rejected.";
        NotificationRecord notificationRecord = notificationService.notifyUser(userID, notification);
        return ResponseEntity.ok(notificationRecord);
    }

    @GetMapping("/notifications")
    @ResponseBody
    public ResponseEntity getNotifications(@RequestParam String userID) {
        List<NotificationRecord> notifications = notificationService.getNotificationsForUser(userID);
        return ResponseEntity.ok(notifications);
    }

    @DeleteMapping("/notifications/{notificationId}")
    @ResponseBody
    public ResponseEntity deleteNotificationRecord(@PathVariable String notificationId) {
        System.out.println("Delete Called");
        notificationService.deleteNotification(notificationId);
        return ResponseEntity.ok("Notification record successfully deleted.");
    }
}
