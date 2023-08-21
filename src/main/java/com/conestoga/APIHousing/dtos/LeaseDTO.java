package com.conestoga.APIHousing.dtos;

import com.conestoga.APIHousing.model.Subresidence;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public class LeaseDTO {
   private Long leaseId;

    private Long userId;
    private AccountDTO user;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date leaseStartDate;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date leaseEndDate;
    private int leaseStatus;

    private Long subresidenceId;
    private SubresidenceDTO subresidence;

    private String unitNo;

    public BigDecimal getLeaseAmount() {
        if (leaseStartDate == null || leaseEndDate == null || subresidence == null || subresidence.getMonthlyRent() == null) {
            return BigDecimal.ZERO;
        }

        LocalDate startDate = leaseStartDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate endDate = leaseEndDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        long monthsBetween = ChronoUnit.MONTHS.between(startDate.withDayOfMonth(1), endDate.withDayOfMonth(1));

        BigDecimal monthlyRent = subresidence.getMonthlyRent();
        return monthlyRent.multiply(BigDecimal.valueOf(monthsBetween));
    }


    public LeaseDTO() {
    // TODO document why this constructor is empty
  }

    public Long getLeaseId() {
        return leaseId;
    }

    public void setLeaseId(Long leaseId) {
        this.leaseId = leaseId;
    }

    // setter and getter for userId
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
 
    public AccountDTO getUser() {
        return user;
    }

    public void setUser(AccountDTO user) {
        this.user = user;
    }

    public Date getLeaseStartDate() {
        return leaseStartDate;
    }

    public void setLeaseStartDate(Date leaseStartDate) {
        this.leaseStartDate = leaseStartDate;
    }

    public Date getLeaseEndDate() {
        return leaseEndDate;
    }

    public void setLeaseEndDate(Date leaseEndDate) {
        this.leaseEndDate = leaseEndDate;
    }


    public int getLeaseStatus() {
        return leaseStatus;
    }

    public void setLeaseStatus(int leaseStatus) {
        this.leaseStatus = leaseStatus;
    }

    // setter and getter for subresidenceId
    public Long getSubresidenceId() {
        return subresidenceId;
    }

    public void setSubresidenceId(Long subresidenceId) {
        this.subresidenceId = subresidenceId;
    }

    public SubresidenceDTO getSubresidence() {
        return subresidence;
    }

    public void setSubresidence(SubresidenceDTO subresidence) {
        this.subresidence = subresidence;
    }

    public String getUnitNo() {
        return unitNo;
    }

    public void setUnitNo(String unitNo) {
        this.unitNo= unitNo;
    }

}
