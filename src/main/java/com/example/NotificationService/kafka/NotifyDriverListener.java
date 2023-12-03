package com.example.NotificationService.kafka;

import com.example.NotificationService.model.KafkaRecord;
import com.example.NotificationService.model.Notification;
import com.example.NotificationService.model.NotificationDetails;
import com.example.NotificationService.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.kafka.annotation.KafkaListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class NotifyDriverListener {
    private static final Logger logger = LoggerFactory.getLogger(NotifyDriverListener.class);

    @Autowired
    private NotificationService notificationService;

    @KafkaListener(topics = "${spring.kafka.topic.name}", groupId = "${spring.kafka.group.id}", containerFactory = "${spring.kafka.containerFactory.name}")
    public void listenNotificationRequest(String testDTO) {
        logger.info("Received a NotifyDriverDTO message from Kafka: {}", testDTO);
        System.out.println("Got kafka message");
        System.out.println(testDTO);

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode jsonNode = objectMapper.readTree(testDTO);
            KafkaRecord kafkaRecord = new KafkaRecord();
            kafkaRecord.setRecipientUserID(jsonNode.get("passengerId").asText());

            NotificationDetails notificationDetails= new NotificationDetails();
            notificationDetails.setNotificationBody(jsonNode.get("message").get("notificationBody").toString());
            notificationDetails.setPassengerID(jsonNode.get("message").get("passengerID").asText());
            notificationDetails.setPostId(jsonNode.get("message").get("postId").asText());
            notificationDetails.setPostTitle(jsonNode.get("message").get("postTitle").asText());

            kafkaRecord.setJoinNotification(notificationDetails);

            notificationService.notifyUser(kafkaRecord.getRecipientUserID(), kafkaRecord.getJoinNotification());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
