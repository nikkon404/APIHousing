package com.conestoga.APIHousing.controller;

import com.conestoga.APIHousing.model.Pin;
import com.conestoga.APIHousing.service.PinService;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ForgetPasswordController {
  private final Map<String, String> resetTokens = new ConcurrentHashMap<>();
  private final PinService pinService;

  private JavaMailSender javaMailSender;

  @Autowired
  public ForgetPasswordController(PinService pinService) {
    this.pinService = pinService;
  }

  @PostMapping("/api/send-pin")
  public ResponseEntity<Map<String, String>> sendPin(@RequestBody Map<String, String> requestBody) {
    String email = requestBody.get("email");

    // Check if the user with the provided email exists in your user database
    // If the email does not exist, return an appropriate response indicating that the email is not
    // registered.
    // Otherwise, proceed with the password reset process.

    // Generate a random reset token
    String resetToken = generateRandomResetToken();

    // Store the reset token in the map (temporary storage)
    resetTokens.put(email, resetToken);

    // Send the reset token to the user's email address
    sendResetTokenToEmail(email, resetToken);

    return ResponseEntity.ok().body(null);
  }

  // validate the reset token
  @PostMapping("/api/validate")
  public ResponseEntity<Boolean> validateResetToken(@RequestBody Map<String, String> requestBody) {
    String email = requestBody.get("email");
    String pin = requestBody.get("pin");

    boolean result = pinService.checkPinByEmailAndPinCode(email, pin);

    // if result is false return bad requese
    if (!result) {
      return ResponseEntity.badRequest().body(result);
    }

    // If all the checks are passed, return a success response.
    return ResponseEntity.ok().build();
  }

  private String generateRandomResetToken() {
    // Generate a random reset token of 4 digits
    Random random = new Random();
    int token = random.nextInt(10000);
    return String.valueOf(token);
  }

  private void sendResetTokenToEmail(String email, String resetToken) {
    try {
      MimeMessage message = javaMailSender.createMimeMessage();
      MimeMessageHelper helper = new MimeMessageHelper(message, true);

      helper.setTo(email);
      helper.setSubject("Password Reset Token");
      helper.setText("Your password reset token is: " + resetToken);

      javaMailSender.send(message);
      pinService.savePin(new Pin(email, resetToken));
    } catch (MessagingException e) {
      e.printStackTrace();
      // Handle the exception or log the error appropriately
    }
  }
}
