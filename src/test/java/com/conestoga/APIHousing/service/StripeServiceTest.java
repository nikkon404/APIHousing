package com.conestoga.APIHousing.service;

import com.stripe.Stripe;
import com.stripe.net.RequestOptions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@TestPropertySource(properties = "Stripe.apiKey=sk_test_51NTuKiLYoxMK8QEvBxNfhsLv9c7eCKkjpiTNTvSVRjdzlDfG47DcKphK3l7L3oB3kKXDDtbHNRAoz9i4n7eotfhY00qJzqVl3I")
class StripeServiceTest {

    @Autowired
    private StripeService stripeService;

    @Value("${Stripe.apiKey}")
    private String stripeKey;

    @BeforeEach
    void setUp() {
        stripeService.init();
    }

    @Test
    void testInit() {
        // Ensure that the Stripe.apiKey is set correctly
        assertEquals(stripeKey, Stripe.apiKey);

        // Ensure that the RequestOptions is set correctly
        RequestOptions requestOptions = RequestOptions.getDefault();
        assertEquals(stripeKey, requestOptions.getApiKey());
    }
}
