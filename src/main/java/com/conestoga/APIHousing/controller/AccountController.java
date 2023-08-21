package com.conestoga.APIHousing.controller;

import com.conestoga.APIHousing.dtos.AccountDTO;
import com.conestoga.APIHousing.dtos.LoginRequest;
import com.conestoga.APIHousing.dtos.LoginResponse;
import com.conestoga.APIHousing.model.Account;
import com.conestoga.APIHousing.service.AccountService;
import com.conestoga.APIHousing.utils.ErrorResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Logger;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

  private static final Logger logger = Logger.getLogger(AccountController.class.getName());

  private final AccountService accountService;

  public AccountController(AccountService accountService) {
    this.accountService = accountService;
  }

  @PostMapping("/login")
  public ResponseEntity<LoginResponse> loginAccount(@RequestBody LoginRequest loginRequest) {
    if (loginRequest.getEmail() == null || loginRequest.getPassword() == null) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    try {
      // Call the loginAccount method in the AccountService
      LoginResponse loginResponse = accountService.loginAccount(loginRequest);

      // Return the LoginResponse object as the response with the appropriate HTTP status
      logger.info("Successfully logged in user: " + loginRequest.getEmail());
      return ResponseEntity.ok(loginResponse);
    } catch (AuthenticationException e) {
      // Handle authentication failure
      logger.warning("Error authenticating user: " + e.getMessage());
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    } catch (Exception e) {
      // Handle other exceptions, log the error, and return an appropriate response
      logger.severe("Error logging in user: " + e.getMessage());
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }

  @PostMapping("/create")
  public ResponseEntity<AccountDTO> createAccount(@RequestBody AccountDTO accountDTO)
      throws IOException {
    logger.info("Creating account: " + accountDTO.getEmail());
    AccountDTO createdAccount = accountService.createAccount(accountDTO);
    return ResponseEntity.status(HttpStatus.CREATED).body(createdAccount);
  }

  @GetMapping("/{id}")
  public ResponseEntity<AccountDTO> getAccountById(@PathVariable Long id) {
    try {
      // Input Validation: Check if the id is not null and greater than 0
      if (id == null || id <= 0) {
        return ResponseEntity.badRequest().build();
      }

      // Use Optional to handle the absence of the account more gracefully
      Optional<AccountDTO> optionalAccountDTO =
          Optional.ofNullable(accountService.getAccountById(id));

      // Check if the account exists, and return it if present
      return optionalAccountDTO
          .map(ResponseEntity::ok)
          .orElseGet(() -> ResponseEntity.notFound().build());
    } catch (NumberFormatException e) {
      // Handle the NumberFormatException and return a meaningful error response
      return ResponseEntity.badRequest().build();
    }
  }

  @PutMapping("/{id}")
  public ResponseEntity<Account> updateAccount(
      @PathVariable Long id, @RequestBody AccountDTO accountDTO) throws IOException {
    Account updatedAccount = accountService.updateAccount(String.valueOf(id), accountDTO);
    if (updatedAccount != null) {
      return ResponseEntity.ok(updatedAccount);
    }
    return ResponseEntity.notFound().build();
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteAccount(@PathVariable Long id) {
    boolean deleted = accountService.deleteAccount(id);
    if (deleted) {
      return ResponseEntity.noContent().build();
    }
    return ResponseEntity.notFound().build();
  }

  @GetMapping("/all")
  public ResponseEntity<List<AccountDTO>> getAllAccounts() {
    List<AccountDTO> accounts = accountService.getAllAccounts();
    return ResponseEntity.ok(accounts);
  }

  @GetMapping("/test")
  public ResponseEntity<String> test2() {
    return ResponseEntity.ok("Test worked!");
  }

  // reset password
  @PostMapping("/reset-password")
  public ResponseEntity<String> resetPassword(@RequestBody Map<String, String> requestBody) {
    String email = requestBody.get("email");
    String password = requestBody.get("password");
    // Check if the user with the provided email exists in your user database
    AccountDTO account = accountService.getAccountByEmail(email);
    if (account == null) {
      return ResponseEntity.badRequest().body("Email not registered");
    } else {
      boolean result = accountService.updatePassword(password, email);
      if (result) {
        return ResponseEntity.ok().body(null);
      } else {
        return ResponseEntity.badRequest().body("Error updating password");
      }
    }
  }

  @ExceptionHandler(DataIntegrityViolationException.class)
  public ResponseEntity<ErrorResponse> handleDataIntegrityViolationException(
      DataIntegrityViolationException ex) {
    // print detailed error message
    logger.warning("Error: " + ex.getMessage());
    String errorMessage = "User with this email already exists.";
    ErrorResponse errorResponse = new ErrorResponse(HttpStatus.CONFLICT, errorMessage);
    logger.warning("Error: " + errorResponse.getMessage());

    return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
  }
}
