package com.conestoga.APIHousing.service;

import com.stripe.Stripe;
import com.stripe.net.RequestOptions;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class StripeService {

    @Value("${Stripe.apiKey}")
    private String stripeKey;

    public void init() {
        Stripe.apiKey = stripeKey;
    }
}
