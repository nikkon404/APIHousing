package com.conestoga.APIHousing.controller;

import com.conestoga.APIHousing.dtos.PaymentRequestDTO;
import com.conestoga.APIHousing.model.Notification;
import com.conestoga.APIHousing.model.Transaction;
import com.conestoga.APIHousing.service.NotificationService;
import com.conestoga.APIHousing.service.TransactionService;
import com.conestoga.APIHousing.utils.Constants;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.exception.StripeException;
import com.stripe.model.*;
import com.stripe.net.Webhook;
import com.stripe.param.CustomerCreateParams;
import com.stripe.param.InvoiceCreateParams;
import com.stripe.param.PaymentIntentCreateParams;
import java.sql.Timestamp;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
public class StripeController {
  final TransactionService transactionService;
  final NotificationService notificationService;
  Logger logger = Logger.getLogger(StripeController.class.getName());

  @Autowired
  public StripeController(
      TransactionService transactionService, NotificationService notificationService) {
    this.transactionService = transactionService;
    this.notificationService = notificationService;
  }

  @PostMapping("/stripe-webhook")
  public ResponseEntity<String> handleStripeWebhook(
      @RequestHeader("Stripe-Signature") String signature, @RequestBody String payload) {
    try {

      logger.info("Payload: " + payload);
      // Verify the Stripe signature
      final String endpointSecret = "whsec_0jerxHwVJ2XBauKOYfQ1XeHBATEwZQCz";
      Event event = Webhook.constructEvent(payload, signature, endpointSecret);

      // Process the Stripe webhook event here
      // Example: Get the event type
      String eventType = event.getType();
      logger.info("Event JSON: " + event.toJson());
      // Deserialize the nested object inside the event
      EventDataObjectDeserializer dataObjectDeserializer = event.getDataObjectDeserializer();
      StripeObject stripeObject = null;
      if (dataObjectDeserializer.getObject().isPresent()) {
        stripeObject = dataObjectDeserializer.getObject().get();
        logger.info("stripeObject: " + stripeObject.toJson());

      } else {
        // Deserialization failed, probably due to an API version mismatch.
        // Refer to the Javadoc documentation on `EventDataObjectDeserializer` for
        // instructions on how to handle this case, or return an error here.
        if (dataObjectDeserializer.getObject().isEmpty()) {
          logger.warning("Deserialization failed, probably due to an API version mismatch.");
        }
      }
      // Handle the event
      if (event.getType().equals("payment_intent.succeeded")) {
        PaymentIntent paymentIntent = (PaymentIntent) stripeObject;
        if (paymentIntent != null) {
          logger.info("Payment for " + paymentIntent.getAmount() + " was successful!");
          handlePaymentIntentSucceeded(paymentIntent);
        }
      } else {
        logger.warning("Unhandled event type: " + event.getType());
      }

      // Add your webhook processing logic here
      logger.info("Webhook received successfully.");
      return ResponseEntity.ok("Webhook received successfully.");
    } catch (SignatureVerificationException e) {
      // Invalid signature, handle accordingly
      logger.warning("Invalid signature.");
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid signature.");
    } catch (Exception e) {
      // Handle other exceptions
      logger.warning("Error processing webhook.");
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
          .body("Error processing webhook.");
    }
  }

  private void handlePaymentIntentSucceeded(PaymentIntent paymentIntent) {
    // now initialize transaction model
    Transaction transaction = new Transaction();

    String payerName = paymentIntent.getMetadata().get("payerName");
    int payerId = Integer.parseInt(paymentIntent.getMetadata().get("payerId"));
    int paymentFor = Integer.parseInt(paymentIntent.getMetadata().get("paymentFor"));
    String externalId = paymentIntent.getMetadata().get("externalId");

    transaction.setPaymentIntentId(paymentIntent.getId());
    transaction.setAmount(paymentIntent.getAmount());
    transaction.setCurrency(paymentIntent.getCurrency());
    transaction.setPaymentStatus(paymentIntent.getStatus());
    transaction.setPaymentMethod(paymentIntent.getPaymentMethod());
    transaction.setPayerId(payerId);
    transaction.setPayerName(payerName);
    transaction.setPaymentFor(paymentFor);
    // set created date as now timestamp
    transaction.setCreatedAt(new Timestamp(System.currentTimeMillis()));
    if (externalId != null) {
      transaction.setExternalId(Integer.parseInt(externalId));
    }
    String description = paymentFor == 1 ? "Rent Payment" : "Event Payment";
    transaction.setDescription(description);

    // save transaction
    try {
      // int to long
      var userId = Long.valueOf(payerId);
      transactionService.createTransaction(transaction);
      notificationService.create(
          new Notification(
              "You paid $" + transaction.getAmount() + " for " + description,
              userId,
              Constants.NOTIFICATION_TYPE_PAYMENT));

    } catch (Exception e) {
      logger.warning("Error saving transaction");
      logger.warning(e.getMessage());
    }
  }

  @GetMapping("/test")
  public void stripe() {
    CustomerCreateParams params =
        CustomerCreateParams.builder()
            .putMetadata("order_id", "6735")
            .setName("Jenny Rosen")
            .setPhone("4158675309")
            .setAddress(
                CustomerCreateParams.Address.builder()
                    .setLine1("510 Townsend St")
                    .setPostalCode("98140")
                    .setCity("San Francisco")
                    .setState("CA")
                    .setCountry("US")
                    .build())
            .setDescription("Example description")
            .setEmail("test@example.com")
            .setPaymentMethod("pm_card_visa") // obtained via Stripe.js
            .build();
    Customer customer = null;
    try {
      customer = Customer.create(params);
      logger.info(customer.getId());
      logger.info(customer.getName());
      logger.info(customer.getPhone());
      logger.info(customer.getAddress().getCity());
      logger.info(customer.getDescription());
      logger.info(customer.getEmail());
    } catch (StripeException ex) {
      logger.warning(ex.getMessage());
    }
  }

  @PostMapping("/pay")
  public ResponseEntity<String> createPayment(@RequestBody PaymentRequestDTO paymentRequest) {
    try {
      // Create a PaymentIntent with the payment request data
      PaymentIntentCreateParams params =
          PaymentIntentCreateParams.builder()
              .setAmount(paymentRequest.getAmount())
              .setCurrency(paymentRequest.getCurrency())
              .setPaymentMethod("pm_card_visa")
              .setCustomer(paymentRequest.getStripeCustomerId())
              // Add any additional parameters, such as metadata or description, if needed
              .build();

      PaymentIntent paymentIntent = PaymentIntent.create(params);
      paymentIntent.confirm();

      // Retrieve the Invoice associated with the PaymentIntent
      Invoice invoice =
          Invoice.create(
              InvoiceCreateParams.builder()
                  .setCustomer(paymentRequest.getStripeCustomerId())
                  .setAutoAdvance(true)
                  .build());
      if (invoice == null) {
        // If the invoice is null, log an error and return an appropriate response
        logger.warning("No associated Invoice found for PaymentIntent: " + paymentIntent.getId());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body("No associated Invoice found for PaymentIntent.");
      } else {
        // Print Invoice details
        logger.info("Invoice ID: " + invoice.getId());
        logger.info("Customer ID: " + invoice.getCustomer());
        logger.info("Customer Email: " + invoice.getCustomerEmail());
        logger.info("Customer Name: " + invoice.getCustomerName());
        logger.info("Customer Phone: " + invoice.getCustomerPhone());
        logger.info(
            "Customer Address: "
                + invoice.getCustomerAddress().getCity()
                + ", "
                + invoice.getCustomerAddress().getCountry());
        logger.info("Amount Paid: " + invoice.getAmountPaid());
      }

      // Return the client secret of the PaymentIntent
      logger.info("Successfully created payment intent: " + paymentIntent.getId());
      return ResponseEntity.ok(paymentIntent.getInvoice());
    } catch (StripeException e) {
      // Handle any exceptions that might occur during the Stripe API call
      // Log the error or notify the user
      logger.severe("Error creating payment intent: " + e.getMessage());
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }
}
