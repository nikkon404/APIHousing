package com.conestoga.APIHousing.interfaces.Booking;

import com.conestoga.APIHousing.model.booking.BookableObject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookableObjectRepository extends JpaRepository<BookableObject, Long> {
    // Custom queries, if needed
}
