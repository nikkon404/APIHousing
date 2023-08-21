package com.conestoga.APIHousing.service;


import com.conestoga.APIHousing.interfaces.NotificationRepository;
import com.conestoga.APIHousing.model.Notification;
import java.util.List;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {
    private final NotificationRepository notificationRepository;
    private final FirebaseService firebaseService;
    private final AccountService accountService;
    Logger logger = Logger.getLogger(NotificationService.class.getName());

    @Autowired
    public NotificationService(NotificationRepository notificationRepository, FirebaseService firebaseService, AccountService accountService) {
        this.notificationRepository = notificationRepository;
        this.firebaseService = firebaseService;
        this.accountService = accountService;
    }

    // find by user id
    public List<Notification> findByUserId(Long userId) {
        logger.info("Notification found for user: " + userId);
        return notificationRepository.findByUserIdOrderByIdDesc(userId);
    }

    // create
    public Notification create(Notification notification) {
        Notification n = notificationRepository.save(notification);
        if (notification.getUserId() != null) {
            logger.info("Notification created for user: " + notification.getUserId());
            String fcm = accountService.getFcmToken(notification.getUserId());
            firebaseService.sendPushNotification(notification.getTitle(), "", fcm);
            logger.info("Notification sent to user: " + notification.getUserId());
            logger.info("Text: " + notification.getTitle());
        }
        return n;
    }
    // String getTitleByType(int type){
    //     switch (type) {
    //         case Constants.NOTIFICATION_TYPE_NOTICE:
    //             return "New Notice";
    //         case Constants.NOTIFICATION_TYPE_EVENT:
    //             return "New Booking";
    //         case Constants.NOTIFICATION_TYPE_REVIEW:
    //             return "New Review";
    //         default:
    //             return "New Notification";
    //     }
    // }

}