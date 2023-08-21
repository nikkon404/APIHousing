package com.conestoga.APIHousing.dtos;

public class PaymentRequestDTO {
    private String paymentMethod;
    private Long amount;
    private String currency;
    private String description;
    private String stripeCustomerId;

    public PaymentRequestDTO() {
    }

    public PaymentRequestDTO(String paymentMethod, Long amount, String currency, String description, String stripeCustomerId) {
        this.paymentMethod = paymentMethod;
        this.amount = amount;
        this.currency = currency;
        this.description = description;
        this.stripeCustomerId = stripeCustomerId;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStripeCustomerId() {
        return stripeCustomerId;
    }

    public void setStripeCustomerId(String stripeCustomerId) {
        this.stripeCustomerId = stripeCustomerId;
    }
}

