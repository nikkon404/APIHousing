package com.conestoga.APIHousing.model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import javax.persistence.*;

@Entity
@Table(name = "transaction")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String paymentIntentId;
    private BigDecimal amount;
    private String currency;
    private String paymentStatus;
    private String payerName;

    @Column(name = "payer_id")
    private int payerId;


    /**
     * 1 is rent, 2 is events
     */
    private int paymentFor;
    private String paymentMethod;
    private Timestamp createdAt;
    private String description;
    private Integer externalId;

    public Transaction() {
    }

    public Transaction(Long id, String paymentIntentId, BigDecimal amount, String currency, String paymentStatus,
                       String payerName, int payerId, int paymentFor, String paymentMethod, Timestamp createdAt, String description,
                       Integer externalId) {
        this.id = id;
        this.paymentIntentId = paymentIntentId;
        this.amount = amount;
        this.currency = currency;
        this.paymentStatus = paymentStatus;
        this.payerName = payerName;
        this.payerId = payerId;
        this.paymentFor = paymentFor;
        this.paymentMethod = paymentMethod;
        this.createdAt = createdAt;
        this.description = description;
        this.externalId = externalId;
    }

    // Getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPaymentIntentId() {
        return paymentIntentId;
    }

    public void setPaymentIntentId(String paymentIntentId) {
        this.paymentIntentId = paymentIntentId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
     public void setAmount(Long amount) {
        // so anount should be divided by 100
        // so if input is 120, amount is 1.20

        this.amount = BigDecimal.valueOf(amount).divide(BigDecimal.valueOf(100));
       
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getPayerName() {
        return payerName;
    }

    public void setPayerName(String payerName) {
        this.payerName = payerName;
    }

    public int getPayerId() {
        return payerId;
    }

    public void setPayerId(int payerId) {
        this.payerId = payerId;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
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

    public Integer getExternalId() {
        return externalId;
    }

    public void setExternalId(Integer externalId) {
        this.externalId = externalId;
    }


    public int getPaymentFor() {
        return paymentFor;
    }

    public void setPaymentFor(int paymentFor) {
        this.paymentFor = paymentFor;
    }
}
