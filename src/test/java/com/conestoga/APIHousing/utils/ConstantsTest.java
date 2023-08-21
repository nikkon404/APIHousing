package com.conestoga.APIHousing.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ConstantsTest {
    @Test
    void testConstantsValues() {
        // Test content type constants
        Assertions.assertEquals("img", Constants.CONTENT_IMG);
        Assertions.assertEquals("txt", Constants.CONTENT_TEXT);

        // Test page size constant
        Assertions.assertEquals(9, Constants.PAGE_SIZE);

        // Test notification type constants
        Assertions.assertEquals(1, Constants.NOTIFICATION_TYPE_PAYMENT);
        Assertions.assertEquals(2, Constants.NOTIFICATION_TYPE_EVENT);
        Assertions.assertEquals(3, Constants.NOTIFICATION_TYPE_MAINTENANCE);
        Assertions.assertEquals(4, Constants.NOTIFICATION_TYPE_ROOMMATE);
        Assertions.assertEquals(5, Constants.NOTIFICATION_TYPE_NOTICE);
        Assertions.assertEquals(6, Constants.NOTIFICATION_TYPE_Booking);
        Assertions.assertEquals(7, Constants.NOTIFICATION_TYPE_Housing);
    }
}
