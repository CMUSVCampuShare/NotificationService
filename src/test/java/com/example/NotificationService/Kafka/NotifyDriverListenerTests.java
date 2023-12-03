package com.example.NotificationService.Kafka;
import com.example.NotificationService.kafka.NotifyDriverListener;
import com.example.NotificationService.model.NotificationDetails;
import com.example.NotificationService.model.NotificationRecord;
import com.example.NotificationService.service.NotificationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.*;

public class NotifyDriverListenerTests {

    @Mock
    private NotificationService notificationService;

    @InjectMocks
    private NotifyDriverListener notifyDriverListener;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void listenNotificationRequest_ReadsMessageFromKafka() {
        NotificationRecord newNotificationRecord = new NotificationRecord();
        newNotificationRecord.setNotificationId("notifId");
        newNotificationRecord.setRecipientId("recipientId");
        NotificationDetails mockNotificationDetails = new NotificationDetails();
        mockNotificationDetails.setNotificationBody("testNotification");
        newNotificationRecord.setNotification(mockNotificationDetails);

        when(notificationService.notifyUser(any(), any())).thenReturn(newNotificationRecord);

        String jsonString = "{\"passengerId\":\"a3c1944d-e537-4a36-ad92-6e2f96b602b8\",\"message\":{\"passengerID\":null,\"postId\":null,\"postTitle\":null,\"notificationBody\":\"Sorry your request has been rejected\"}}";

        notifyDriverListener.listenNotificationRequest(jsonString);

        verify(notificationService, times(1)).notifyUser(any(), any());
    }
}
