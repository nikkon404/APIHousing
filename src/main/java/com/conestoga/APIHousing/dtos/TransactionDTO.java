package com.conestoga.APIHousing.dtos;

import com.conestoga.APIHousing.model.Transaction;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class TransactionDTO {
    private Long id;
    private BigDecimal amount;
    // @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    //eastern time zone
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS", timezone = "America/New_York")
    private Timestamp createdAt;
    private String description;
    private int paymentFor;
    private int payerId;
    private String payerName;

    public TransactionDTO() {
    }

    public TransactionDTO(Long id, BigDecimal amount, Timestamp createdAt, String description, int paymentFor, int payerId, String payerName) {
        this.id = id;
        this.amount = amount;
        this.createdAt = createdAt;
        this.description = description;
        this.paymentFor = paymentFor;
        this.payerId = payerId;
        this.payerName = payerName;
    }

    //TransactionDTO from Transaction constructor
    public TransactionDTO(Transaction transaction) {
        this.id = transaction.getId();
        this.amount = transaction.getAmount();
        this.createdAt = transaction.getCreatedAt();
        this.description = transaction.getDescription();
        this.paymentFor = transaction.getPaymentFor();
        this.payerId = transaction.getPayerId();
        this.payerName = transaction.getPayerName();
    }

    // Getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPaymentFor() {
        return paymentFor;
    }

    public void setPaymentFor(int paymentFor) {
        this.paymentFor = paymentFor;
    }

    public int getPayerId() {
        return payerId;
    }

    public void setPayerId(int payerId) {
        this.payerId = payerId;
    }

    public void setPayerName(String payerName) {
        this.payerName = payerName;
    }

    public String getPayerName() {
        return payerName;
    }

}
