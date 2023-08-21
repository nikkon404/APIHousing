package com.conestoga.APIHousing.service.Booking;

import com.conestoga.APIHousing.interfaces.AccountRepository;
import com.conestoga.APIHousing.interfaces.Booking.BookableObjectRepository;
import com.conestoga.APIHousing.interfaces.Booking.BookingRepository;
import com.conestoga.APIHousing.model.booking.BookableObject;
import com.conestoga.APIHousing.model.booking.Booking;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookingService {
    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private AccountRepository accountRepository;


    @Autowired
    private BookableObjectRepository bookableObjectRepository;

    public List<BookableObject> getAllBookableObjects() {
        return bookableObjectRepository.findAll();
    }

    public void bookItem(BookableObject item, Long userId, LocalDate bookedFrom, LocalDate bookedTo) {
        bookableObjectRepository.save(item);

        Booking booking = new Booking();
        booking.setItem(item);
        booking.setUser(accountRepository.getById(userId));
        booking.setBookedFrom(bookedFrom);
        booking.setBookedTo(bookedTo);
        bookingRepository.save(booking);
    }

   public boolean isItemBooked(Long itemId) {
    return bookingRepository.existsByItemId(itemId);
}

    public List<Booking> getBookingsForUser(Long userId) {
        return bookingRepository.findByUserId(userId);
    }

    public void updateBookedStatus(Long itemId, boolean isBooked) {
        Optional<BookableObject> optionalItem = bookableObjectRepository.findById(itemId);
        optionalItem.ifPresent(item -> {
            bookableObjectRepository.save(item);
        });
    }

    public BookableObject getBookableItemById(Long itemId) {
        Optional<BookableObject> optionalItem = bookableObjectRepository.findById(itemId);
        return optionalItem.orElse(null);
    }

    public List<Booking> getBookedDatesForItem(Long id) {
        return bookingRepository.findByItemId(id);
    }
}
