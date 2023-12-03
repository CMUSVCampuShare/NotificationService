package com.example.NotificationService.service;

import com.example.NotificationService.model.NotificationDetails;
import com.example.NotificationService.model.NotificationRecord;
import com.example.NotificationService.repository.NotificationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class NotificationServiceTests {
    @Mock
    private NotificationRepository notificationRepository;

    @Mock
    private SimpMessagingTemplate simpMessagingTemplate;
    @InjectMocks
    private NotificationService notificationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void notifyUserCreatesWebsocket() {
        String userId = "userId";
        Object notification = new Object();
        NotificationDetails notificationDetails = new NotificationDetails();
        notificationDetails.setNotificationBody(notification);

        String notificationId = "notificationId";
        NotificationRecord mockNotificationRecord = new NotificationRecord();
        mockNotificationRecord.setRecipientId(userId);
        mockNotificationRecord.setNotification(notificationDetails);
        mockNotificationRecord.setNotificationId(notificationId);

        when(notificationRepository.save(any())).thenReturn(mockNotificationRecord);
        doNothing().when(simpMessagingTemplate).convertAndSendToUser(userId, "/notification", notification);

        NotificationRecord savedNotification = notificationService.notifyUser(userId, notificationDetails);
        assertNotNull(savedNotification);
        assertEquals(notificationId, savedNotification.getNotificationId());
        assertEquals(userId, savedNotification.getRecipientId());
        assertEquals(notificationDetails, savedNotification.getNotification());
        verify(notificationRepository, times(1)).save(any());

    }

    @Test
    void getNotificationsForUser_ReturnsAllRecords(){
        String userId = "userId";
        NotificationDetails notification = new NotificationDetails();

        String notificationId = "notificationId";
        NotificationRecord mockNotificationRecord = new NotificationRecord();
        mockNotificationRecord.setRecipientId(userId);
        mockNotificationRecord.setNotification(notification);
        mockNotificationRecord.setNotificationId(notificationId);

        List<NotificationRecord> mockNotificationRecords = Collections.singletonList(mockNotificationRecord);
        when(notificationRepository.findByRecipientId(userId)).thenReturn(mockNotificationRecords);

        List<NotificationRecord> notificationRecords = notificationService.getNotificationsForUser(userId);

        assertEquals(mockNotificationRecords, notificationRecords);
        verify(notificationRepository, times(1)).findByRecipientId(userId);
    }

    @Test
    void deleteNotification_DeletesNotification() {
        String userId = "userId";
        NotificationDetails notification = new NotificationDetails();

        String notificationId = "notificationId";
        NotificationRecord mockNotificationRecord = new NotificationRecord();
        mockNotificationRecord.setRecipientId(userId);
        mockNotificationRecord.setNotification(notification);
        mockNotificationRecord.setNotificationId(notificationId);

        when(notificationRepository.deleteByNotificationId(notificationId)).thenReturn(mockNotificationRecord);

        notificationService.deleteNotification(notificationId);

        verify(notificationRepository, times(1)).deleteByNotificationId(notificationId);
    }
}
