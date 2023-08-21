package com.conestoga.APIHousing.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

class ErrorResponseTest {
    @Test
    void testErrorResponseConstructorAndGetterMethods() {
        // Test data
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String message = "This is an error message.";

        // Create an instance of ErrorResponse using the constructor
        ErrorResponse errorResponse = new ErrorResponse(status, message);

        // Verify that the status and message fields are set correctly
        Assertions.assertEquals(status, errorResponse.getStatus());
        Assertions.assertEquals(message, errorResponse.getMessage());
    }
}
