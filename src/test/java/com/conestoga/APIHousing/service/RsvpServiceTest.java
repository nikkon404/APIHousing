package com.conestoga.APIHousing.service;

import com.conestoga.APIHousing.interfaces.AccountRepository;
import com.conestoga.APIHousing.interfaces.EventRepository;
import com.conestoga.APIHousing.interfaces.RsvpRepository;
import com.conestoga.APIHousing.model.Account;
import com.conestoga.APIHousing.model.Event;
import com.conestoga.APIHousing.model.Rsvp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
@SpringBootTest
@AutoConfigureMockMvc
class RsvpServiceTest {

  @Mock
  private RsvpRepository rsvpRepository;

  @Mock
  private EventRepository eventRepository;

  @Mock
  private AccountRepository accountRepository;

  @InjectMocks
  private RsvpService rsvpService;

  @BeforeEach
  void setUp() {
    // Reset the mock before each test
    reset(rsvpRepository);
  }
  @Test
  void testRsvpForEvent() {
    Long eventId = 1L;
    Long userId = 2L;

    Event event = new Event();
    Account user = new Account();

    when(eventRepository.findById(eventId)).thenReturn(Optional.of(event));
    when(accountRepository.findById(userId)).thenReturn(Optional.of(user));

    boolean result = rsvpService.rsvpForEvent(eventId, userId);

    assertTrue(result);
    verify(rsvpRepository, times(1)).save(any(Rsvp.class));
  }

  @Test
  void testCancelRsvpForEvent() {
    Long eventId = 1L;
    Long userId = 2L;

    Rsvp rsvp = new Rsvp();

    // Mock the behavior of the rsvpRepository.findByEventIdAndUserId() method
    when(rsvpRepository.findByEventIdAndUserId(eventId, userId)).thenReturn(Optional.of(rsvp));

    // Call the method being tested
    boolean result = rsvpService.cancelRsvpForEvent(eventId, userId);

    assertTrue(result);

    // Verify that the delete method was called on the rsvpRepository with the correct rsvp object
    verify(rsvpRepository, times(1)).delete(rsvp);
  }

  @Test
  void testFindByEventIdAndUserId() {
    Long eventId = 1L;
    Long userId = 2L;

    Rsvp rsvp = new Rsvp();

    when(rsvpRepository.findByEventIdAndUserId(eventId, userId)).thenReturn(Optional.of(rsvp));

    Optional<Rsvp> result = rsvpService.findByEventIdAndUserId(eventId, userId);

    assertTrue(result.isPresent());
    assertSame(rsvp, result.get());
  }

  @Test
  void testFindByEventIdAndUserId_NullInputs() {
    Optional<Rsvp> result = rsvpService.findByEventIdAndUserId(null, null);

    assertFalse(result.isPresent());
    verifyNoInteractions(rsvpRepository);
  }
}
