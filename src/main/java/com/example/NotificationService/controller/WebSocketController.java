package com.example.NotificationService.controller;

import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

@Controller
public class WebSocketController {

    private final SimpMessagingTemplate messagingTemplate;

    public WebSocketController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }
    @MessageMapping("/send-text/{username}")
    public void sendTextToUser(@DestinationVariable String username, Object notification) {
        username = HtmlUtils.htmlEscape(username);
        messagingTemplate.convertAndSendToUser(username, "/topic/text", notification);
    }
}
