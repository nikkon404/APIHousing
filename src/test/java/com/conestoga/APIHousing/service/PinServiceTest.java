package com.conestoga.APIHousing.service;

import com.conestoga.APIHousing.interfaces.PinRepository;
import com.conestoga.APIHousing.model.Pin;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@AutoConfigureMockMvc
class PinServiceTest {

  @Mock private PinRepository pinRepository;

  @InjectMocks private PinService pinService;

  @Test
  void testSavePin() {
    Pin pin = new Pin();
    pin.setEmail("test@example.com");
    pin.setPinCode("1234");

    when(pinRepository.save(pin)).thenReturn(pin);

    Pin savedPin = pinService.savePin(pin);

    assertNotNull(savedPin);
    assertEquals("test@example.com", savedPin.getEmail());
    assertEquals("1234", savedPin.getPinCode());

    verify(pinRepository, times(1)).save(pin);
  }

  @Test
  void testCheckPinByEmailAndPinCode() {
    String email = "test@example.com";
    String pinCode = "1234";

    Pin pin = new Pin();
    pin.setEmail(email);
    pin.setPinCode(pinCode);

    when(pinRepository.findPinByEmail(email, pinCode)).thenReturn(pin);

    boolean result = pinService.checkPinByEmailAndPinCode(email, pinCode);

    assertTrue(result);

    verify(pinRepository, times(1)).findPinByEmail(email, pinCode);
  }

  @Test
  void testCheckPinByEmailAndPinCode_NullInputs() {
    String email = null;
    String pinCode = null;

    boolean result = pinService.checkPinByEmailAndPinCode(email, pinCode);

    assertFalse(result);

    verifyNoInteractions(pinRepository);
  }
}
