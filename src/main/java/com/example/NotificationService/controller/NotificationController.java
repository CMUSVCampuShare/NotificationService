package com.example.NotificationService.controller;

import com.example.NotificationService.model.Notification;
import com.example.NotificationService.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @PostMapping("/notify")
    @ResponseBody
    public ResponseEntity notifyUser(@RequestParam Integer userID, @RequestBody Notification notification){
        notificationService.notifyUser(userID, notification);
        return ResponseEntity.ok("Notification Sent!");
    }

}
