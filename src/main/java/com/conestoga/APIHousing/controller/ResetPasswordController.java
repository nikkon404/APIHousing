package com.conestoga.APIHousing.controller;

import com.conestoga.APIHousing.dtos.ResetPasswordRequestDTO;
import com.conestoga.APIHousing.interfaces.AccountRepository;
import com.conestoga.APIHousing.model.Account;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ResetPasswordController {
    private static final long RESET_TOKEN_EXPIRATION_MINUTES = 30;
    private static final Map<String, ResetTokenData> resetTokens = new ConcurrentHashMap<>();

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private PasswordEncoder passwordEncoder; // Autowire the PasswordEncoder bean

    @Autowired
    private AccountRepository accountRepository;

    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody ResetPasswordRequestDTO request) {
        String email = request.getEmail();
        String resetToken = request.getResetToken();
        String newPassword = request.getNewPassword();

        // Check if the user with the provided email exists in your user database
        // If the email does not exist, return an appropriate response indicating that the email is not registered.
        // Otherwise, proceed with the password reset process.
        // Find the account by email in the database
        Account account = accountRepository.findByEmail(email).orElse(null);

        if (account == null) {
            // If the account with the provided email doesn't exist, return an error response
            return ResponseEntity.badRequest().body("Account not found.");
        }

        // Get the stored reset token and its creation time from the map
        ResetTokenData resetTokenData = resetTokens.get(email);
        if (resetTokenData == null || !resetTokenData.isValid(resetToken)) {
            return ResponseEntity.badRequest().body("Invalid reset token or expired reset token.");
        }

        // Check if the reset token is still valid (not expired)
        if (resetTokenData.isExpired()) {
            // If the token is expired, remove it from the map and return an error response
            resetTokens.remove(email);
            return ResponseEntity.badRequest().body("Expired reset token.");
        }

        // If the token is valid, proceed with the password reset
        // Here, you would implement the logic to update the user's password in your user database
        // For demonstration purposes, we'll just print the new password here
        // Update the password using the Account entity
       // account.setPassword(newPassword);

        // Save the updated account in the database
        accountRepository.save(account);

        // Remove the reset token from the map as it has been used
        resetTokens.remove(email);

        return ResponseEntity.ok("Password reset successful.");
    }

    private record ResetTokenData(String resetToken, LocalDateTime creationTime) {

        public boolean isValid(String tokenToCheck) {
            // Check if the provided token matches the stored token
            return resetToken.equals(tokenToCheck) && !isExpired();
        }

        public boolean isExpired() {
            // Check if the token has expired based on the configured expiration time
//            return Duration.between(creationTime, LocalDateTime.now()).toMinutes() >= RESET_TOKEN_EXPIRATION_MINUTES;
            LocalDateTime now = LocalDateTime.now(ZoneId.of("America/Toronto")); // Replace "Your_Time_Zone" with the appropriate time zone
            return Duration.between(creationTime, now).toMinutes() >= RESET_TOKEN_EXPIRATION_MINUTES;
        }
    }
}

