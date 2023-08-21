package com.conestoga.APIHousing.model.booking;

import com.conestoga.APIHousing.model.Account;
import com.fasterxml.jackson.annotation.JsonFormat; 
import java.time.LocalDate;
import javax.persistence.*;

@Entity
@Table(name = "booking")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Account user;

    @ManyToOne
    @JoinColumn(name = "item_id")
    private BookableObject item;

    @Column(name = "booked_from")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate bookedFrom;

    @Column(name = "booked_to")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate bookedTo;

    public Booking() {
    }

    public Booking(Long id, Account user, BookableObject item, LocalDate bookedFrom, LocalDate bookedTo) {
        this.id = id;
        this.user = user;
        this.item = item;
        this.bookedFrom = bookedFrom;
        this.bookedTo = bookedTo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Account getUser() {
        return user;
    }

    public void setUser(Account user) {
        this.user = user;
    }

    public BookableObject getItem() {
        return item;
    }

    public void setItem(BookableObject item) {
        this.item = item;
    }

    public LocalDate getBookedFrom() {
        return bookedFrom;
    }

    public void setBookedFrom(LocalDate bookedFrom) {
        this.bookedFrom = bookedFrom;
    }

    public LocalDate getBookedTo() {
        return bookedTo;
    }

    public void setBookedTo(LocalDate bookedTo) {
        this.bookedTo = bookedTo;
    }

}
