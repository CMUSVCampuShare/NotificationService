package com.example.NotificationService.kafka;

import com.example.NotificationService.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.kafka.annotation.KafkaListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class NotifyDriverListener {
    private static final Logger logger = LoggerFactory.getLogger(NotifyDriverListener.class);

    @Autowired
    private NotificationService notificationService;

    @KafkaListener(topics = "${spring.kafka.topic.name}", groupId = "${spring.kafka.group.id}", containerFactory = "${spring.kafka.containerFactory.name}")
    public void listenNotificationRequest(String testDTO) {
        logger.info("Received a NotifyDriverDTO message from Kafka: {}", testDTO);
        notificationService.notifyUser("15", null);
    }

}
