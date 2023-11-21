package com.example.NotificationService.controller;
import com.example.NotificationService.model.Notification;
import com.example.NotificationService.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
        notificationService.notifyUser(userID, notification);
        return ResponseEntity.ok("Driver has received your request!");
    }
}
