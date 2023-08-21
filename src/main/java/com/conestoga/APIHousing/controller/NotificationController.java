package com.conestoga.APIHousing.controller;

import com.conestoga.APIHousing.model.Notification;
import com.conestoga.APIHousing.model.NotificationRequest;
import com.conestoga.APIHousing.service.FirebaseService;
import com.conestoga.APIHousing.service.NotificationService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    private final FirebaseService firebaseService;
    private final NotificationService notificationService;


    @Autowired
    public NotificationController(FirebaseService firebaseService, NotificationService notificationService) {
        this.firebaseService = firebaseService;
        this.notificationService = notificationService;
    }

    @PostMapping("/create")
    public ResponseEntity<String> createNotification(@RequestBody NotificationRequest request) {
        try {
           firebaseService.sendPushNotification(request.getTitle(), request.getDescription());
            return ResponseEntity.ok("Notification sent successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to send notification: " + e.getMessage());
        }
    }

    //get all notifications for a user
    @GetMapping("/get/{userId}")
    public ResponseEntity<List<Notification>> getNotifications(@PathVariable Long userId) {
        List<Notification> notifications = notificationService.findByUserId(userId);
        return new ResponseEntity<>(notifications, HttpStatus.OK);
        
    }
    
}
