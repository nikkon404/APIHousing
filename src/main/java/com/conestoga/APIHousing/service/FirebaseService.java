package com.conestoga.APIHousing.service;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

@Service
public class FirebaseService {

    private static final Logger logger = Logger.getLogger(FirebaseService.class.getName());
    private static FirebaseApp firebaseApp;

    @PostConstruct
    public static void initializeFirebase() throws IOException {
        if (firebaseApp == null) {
            InputStream serviceAccount = new ClassPathResource("firebase/serviceAccountKey.json").getInputStream();

            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .build();

            firebaseApp = FirebaseApp.initializeApp(options);
        }
    }

    public void sendPushNotification(String title, String description) {
        sendPushNotification(title, description, null); // Call the overloaded method with null FCM token
    }

    public void sendPushNotification(String title, String description, String fcmToken) {
        Notification notification = Notification.builder()
                .setTitle(title)
                .setBody(description)
                .build();

        Message.Builder messageBuilder = Message.builder()
                .setNotification(notification);

        if (fcmToken != null) {
            messageBuilder.setToken(fcmToken); // Set the FCM token if provided
        } else {
            messageBuilder.setTopic("topic"); // Use a default topic if FCM token is not provided
        }

        Message message = messageBuilder.build();

        try {
            String response = FirebaseMessaging.getInstance().send(message);
            logger.info("Successfully sent message: " + response);
        } catch (Exception e) {
            logger.warning("Error sending message: " + e.getMessage());
        }
    }

    public void getAllNotifications() {
        //Does not exist yet as API
    }
}
