package com.example.NotificationService.Kafka;
import com.example.NotificationService.kafka.NotifyDriverListener;
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
        newNotificationRecord.setNotification(new Object());

        when(notificationService.notifyUser(any(), any())).thenReturn(newNotificationRecord);

        String jsonString = "{\"message\":\"Notification has been created\",\"status\":\"CREATED\",\"recipientUserID\":\"userId\",\"joinNotification\":\"abcdefg\"}";

        notifyDriverListener.listenNotificationRequest(jsonString);

        verify(notificationService, times(1)).notifyUser(any(), any());
    }
}
