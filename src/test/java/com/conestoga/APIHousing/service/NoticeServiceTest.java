package com.conestoga.APIHousing.service;

import com.conestoga.APIHousing.interfaces.NoticeRepository;
import com.conestoga.APIHousing.model.Notice;
import com.conestoga.APIHousing.service.NoticeService;
import com.conestoga.APIHousing.utils.FileUpload;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@AutoConfigureMockMvc
class NoticeServiceTest {

    @Mock
    private NoticeRepository noticeRepository;

    @InjectMocks
    private NoticeService noticeService;

    @Test
    void testGetAllNotices() {
        // Create some sample notices
        Notice notice1 = new Notice();
        notice1.setId(1L);
        notice1.setTitle("Notice 1");

        Notice notice2 = new Notice();
        notice2.setId(2L);
        notice2.setTitle("Notice 2");

        List<Notice> notices = new ArrayList<>();
        notices.add(notice1);
        notices.add(notice2);

        // Mock the findAll() method of the noticeRepository to return the sample notices
        when(noticeRepository.findAll()).thenReturn(notices);

        // Call the method to get all notices
        List<Notice> result = noticeService.getAllNotices();

        // Verify that the returned list of notices matches the sample list
        assertEquals(notices, result);
    }

    @Test
    void testCreateNoticeWithImage() throws IOException {
        // Create a sample notice with image
        Notice notice = new Notice();
        notice.setTitle("Test Notice");
        notice.setImgUrl("base64encodedimage");

        // Mock the save() method of the noticeRepository to return the sample notice
        when(noticeRepository.save(any(Notice.class))).thenReturn(notice);

        // Call the createNotice() method
        Notice createdNotice = noticeService.createNotice(notice);

        // Verify that the noticeRepository's save() method was called
        verify(noticeRepository, times(1)).save(any(Notice.class));

        // Verify that the created notice has a non-empty imageUrl
        assertNotNull(createdNotice.getImgUrl());
        assertFalse(createdNotice.getImgUrl().isEmpty());
    }

    @Test
    void testCreateNoticeWithoutImage() throws IOException {
        // Create a sample notice without image
        Notice notice = new Notice();
        notice.setTitle("Test Notice");

        // Mock the save() method of the noticeRepository to return the sample notice
        when(noticeRepository.save(any(Notice.class))).thenReturn(notice);

        // Call the createNotice() method
        Notice createdNotice = noticeService.createNotice(notice);

        // Verify that the noticeRepository's save() method was called
        verify(noticeRepository, times(1)).save(any(Notice.class));

        // Verify that the created notice has a null imageUrl
        assertNull(createdNotice.getImgUrl());
    }

    @Test
    void testDeleteNotice() {
        // Define the notice ID to be deleted
        Long noticeId = 1L;

        // Call the deleteNotice() method
        noticeService.deleteNotice(noticeId);

        // Verify that the noticeRepository's deleteById() method was called with the correct ID
        verify(noticeRepository, times(1)).deleteById(noticeId);
    }

    // Add more test cases for other methods in NoticeService as needed
}

