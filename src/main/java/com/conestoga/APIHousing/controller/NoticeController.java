package com.conestoga.APIHousing.controller;

import com.conestoga.APIHousing.model.Notice;
import com.conestoga.APIHousing.model.Notification;
import com.conestoga.APIHousing.service.NoticeService;
import com.conestoga.APIHousing.service.NotificationService;
import com.conestoga.APIHousing.utils.Constants;
import java.io.IOException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/notices")
public class NoticeController {

    private final NoticeService noticeService;
    private final NotificationService notificationService;


    @Autowired
    public NoticeController(NoticeService noticeService, NotificationService notificationService) {
        this.noticeService = noticeService;
        this.notificationService = notificationService;
    }

    @GetMapping
    public ResponseEntity<List<Notice>> getAllNotices() {
        List<Notice> notices = noticeService.getAllNotices();
        return ResponseEntity.ok(notices);
    }

   
    @PostMapping
    public ResponseEntity<Notice> createNotice(@RequestBody Notice Notice) throws IOException {
        Notice createdNotice = noticeService.createNotice(Notice);
        notificationService.create(new Notification("New Notice: "+createdNotice.getTitle(), null, Constants.NOTIFICATION_TYPE_NOTICE));
        return ResponseEntity.status(HttpStatus.CREATED).body(createdNotice);
    }
    

    //delete notice
    @DeleteMapping("/{id}")
    public ResponseEntity<Notice> deleteNotice(@PathVariable Long id) {
        noticeService.deleteNotice(id);
        return ResponseEntity.ok().build();
    }

    // Add other endpoints for updating and deleting notices (omitted for brevity)
}
