package com.conestoga.APIHousing.service;

import com.conestoga.APIHousing.interfaces.NotificationRepository;
import com.conestoga.APIHousing.model.Notification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
class NotificationServiceTest {

    @Mock
    private NotificationRepository notificationRepository;

    @Mock
    private FirebaseService firebaseService;

    @Mock
    private AccountService accountService;

    @InjectMocks
    private NotificationService notificationService;

    private Logger logger;

    @BeforeEach
    void setUp() {
        // Reset the mock before each test
        reset(notificationRepository, firebaseService, accountService);
        logger = Logger.getLogger(NotificationService.class.getName());
        notificationService = new NotificationService(notificationRepository, firebaseService, accountService);
    }

    @Test
    void testFindByUserId() {
        Long userId = 1L;

        // Create a list of dummy notifications for the given user ID
        List<Notification> notifications = Collections.singletonList(new Notification());

        // Mock the behavior of the notificationRepository.findByUserIdOrderByIdDesc() method
        when(notificationRepository.findByUserIdOrderByIdDesc(userId)).thenReturn(notifications);

        // Call the method being tested
        List<Notification> result = notificationService.findByUserId(userId);

        // Verify that the result matches the expected list of notifications
        assertEquals(notifications, result);
        verify(notificationRepository, times(1)).findByUserIdOrderByIdDesc(userId);
    }

    @Test
    void testCreateWithUserId() {
        Long userId = 1L;
        Notification notification = new Notification();
        notification.setUserId(userId);
        notification.setTitle("Test Notification");

        // Mock the behavior of the notificationRepository.save() method
        when(notificationRepository.save(notification)).thenReturn(notification);

        // Mock the behavior of the accountService.getFcmToken() method
        when(accountService.getFcmToken(userId)).thenReturn("test_fcm_token");

        // Call the method being tested
        Notification result = notificationService.create(notification);

        // Verify that the result matches the expected notification
        assertEquals(notification, result);
        verify(notificationRepository, times(1)).save(notification);

        // Verify that the firebaseService.sendPushNotification() method was called with the correct arguments
        verify(firebaseService, times(1)).sendPushNotification(notification.getTitle(), "", "test_fcm_token");
    }

    @Test
    void testCreateWithoutUserId() {
        Notification notification = new Notification();
        notification.setTitle("Test Notification");

        // Mock the behavior of the notificationRepository.save() method
        when(notificationRepository.save(notification)).thenReturn(notification);

        // Call the method being tested
        Notification result = notificationService.create(notification);

        // Verify that the result matches the expected notification
        assertEquals(notification, result);
        verify(notificationRepository, times(1)).save(notification);

        // Verify that the firebaseService.sendPushNotification() method was not called (userId is null)
        verify(firebaseService, never()).sendPushNotification(any(), any(), any());
    }
}
