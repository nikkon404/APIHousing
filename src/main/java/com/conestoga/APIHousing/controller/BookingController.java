package com.conestoga.APIHousing.controller;

import com.conestoga.APIHousing.model.Notification;
import com.conestoga.APIHousing.model.booking.BookableObject;
import com.conestoga.APIHousing.model.booking.Booking;
import com.conestoga.APIHousing.service.Booking.BookingService;
import com.conestoga.APIHousing.service.NotificationService;
import com.conestoga.APIHousing.utils.Constants;
import com.conestoga.APIHousing.utils.ErrorResponse;
import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {
    @Autowired
    private BookingService bookingService;

    @Autowired
    private NotificationService notificationService;


    @GetMapping("/items")
    public List<BookableObject> getAllBookableItems() {
        return bookingService.getAllBookableObjects();
    }

    //get booked dates for a bookable item
    @GetMapping("/items/{id}")
    public List<Booking> getBookedDatesForItem(@PathVariable Long id) {
        return bookingService.getBookedDatesForItem(id);
    }

    @PostMapping("/book")
    public ResponseEntity<Object> bookItem(@RequestBody BookingRequest request) {
        // You'll need to implement user authentication and retrieve the logged-in user
       

        BookableObject item = bookingService.getBookableItemById(request.getItemId());
        if (item == null) {
            return ResponseEntity.badRequest().body(new ErrorResponse(HttpStatus.BAD_REQUEST, "Bookable item not found."));
            
        }

  

        LocalDate bookedFrom = LocalDate.parse(request.getBookedFrom());
        LocalDate bookedTo = LocalDate.parse(request.getBookedTo());

        //prepare readale date string for notification. like July 1, 2021 to July 5, 2021
        String readableDate = bookedFrom.getMonth().name() + " " + bookedFrom.getDayOfMonth() + ", " + bookedFrom.getYear() + " to " + bookedTo.getMonth().name() + " " + bookedTo.getDayOfMonth() + ", " + bookedTo.getYear();

         notificationService.create(new Notification("You booked "+item.getName()+" from "+readableDate, request.getUserId(), Constants.NOTIFICATION_TYPE_Booking));


        bookingService.bookItem(item, request.userId, bookedFrom, bookedTo);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/status/{itemId}")
    public ResponseEntity<String> getItemStatus(@PathVariable Long itemId) {
        boolean isBooked = bookingService.isItemBooked(itemId);
        return ResponseEntity.ok(isBooked ? "Booked" : "Available");
    }

   @GetMapping("/user-bookings/{userId}")
public List<Booking> getUserBookings(@PathVariable Long userId) {
    return bookingService.getBookingsForUser(userId);
}
    @PutMapping("/update-status/{itemId}/{isBooked}")
    public ResponseEntity<String> updateItemStatus(@PathVariable Long itemId, @PathVariable boolean isBooked) {
        bookingService.updateBookedStatus(itemId, isBooked);
        return ResponseEntity.ok("Item status updated successfully.");
    }

    // Helper class to hold booking request data
    static class BookingRequest {
        private Long itemId;
        private Long userId;
        private String bookedFrom;
        private String bookedTo;

        // Getters and Setters

        public Long getItemId() {
            return itemId;
        }

        public void setItemId(Long itemId) {
            this.itemId = itemId;
        }

        public String getBookedFrom() {
            return bookedFrom;
        }

        public void setBookedFrom(String bookedFrom) {
            this.bookedFrom = bookedFrom;
        }

        public String getBookedTo() {
            return bookedTo;
        }

        public void setBookedTo(String bookedTo) {
            this.bookedTo = bookedTo;
        }

        public Long getUserId() {
            return userId;
        }

        public void setUserId(Long userId) {
            this.userId = userId;
        }


    }
}
