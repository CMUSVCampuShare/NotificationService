package com.example.NotificationService.controller;
import com.example.NotificationService.model.NotificationDetails;
import com.example.NotificationService.model.NotificationRecord;
import com.example.NotificationService.service.NotificationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class NotificationControllerTests {
    @Mock
    private NotificationService notificationService;

    @InjectMocks
    private NotificationController notificationController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void getNotifications_returnsAllNotificationsForTheUser() {
        String userId = "userId";

        NotificationRecord newNotificationRecord = new NotificationRecord();
        newNotificationRecord.setNotificationId("notifId");
        newNotificationRecord.setRecipientId("recipientId");
        newNotificationRecord.setNotification(new NotificationDetails());
        List<NotificationRecord> mockNotifications = Collections.singletonList(newNotificationRecord);

        when(notificationService.getNotificationsForUser(userId)).thenReturn(mockNotifications);

        ResponseEntity<List<NotificationRecord>> response = notificationController.getNotifications(userId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockNotifications, response.getBody());
        verify(notificationService, times(1)).getNotificationsForUser(userId);
    }

    @Test
    void deleteNotificationRecord_FailsWhenNoRecord() {
        String notificationId = "notifId";
        ResponseEntity<List<NotificationRecord>> response = notificationController.deleteNotificationRecord(notificationId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Notification record successfully deleted.", response.getBody());
        verify(notificationService, times(1)).deleteNotification(notificationId);
    }

}
