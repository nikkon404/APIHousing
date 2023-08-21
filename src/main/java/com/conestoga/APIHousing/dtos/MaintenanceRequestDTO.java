package com.conestoga.APIHousing.dtos;

import com.conestoga.APIHousing.model.MaintenanceRequest;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.Date;

public class MaintenanceRequestDTO {
    private Long requestId;
    private Long unitId;
    private Long userId;
    //should be date and time
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date requestDate;
    private String requestDescription;
    private int requestStatus;
    private String img;
    private String remarks;

    public MaintenanceRequestDTO(long requestId, long unitId, long userId, Date requestDate, String requestDescription, int requestStatus, String img, String remarks) {
        this.requestId = requestId;
        this.unitId = unitId;
        this.userId = userId;
        this.requestDate = requestDate;
        this.requestDescription = requestDescription;
        this.requestStatus = requestStatus;
        this.img = img;
        this.remarks = remarks;
    }

    public MaintenanceRequestDTO() {

    }

    // Getters and Setters

    public static MaintenanceRequestDTO fromModel(MaintenanceRequest maintenanceRequest) {
        MaintenanceRequestDTO maintenanceRequestDTO = new MaintenanceRequestDTO();
        maintenanceRequestDTO.setRequestId(maintenanceRequest.getRequestId());
        maintenanceRequestDTO.setUnitId(maintenanceRequest.getUnit().getUnitId());
        maintenanceRequestDTO.setUserId(maintenanceRequest.getUser().getUserId());
        maintenanceRequestDTO.setRequestDate(maintenanceRequest.getRequestDate());
        maintenanceRequestDTO.setRequestDescription(maintenanceRequest.getRequestDescription());
        maintenanceRequestDTO.setRequestStatus(maintenanceRequest.getRequestStatus());
        maintenanceRequestDTO.setImg(maintenanceRequest.getImg());
        maintenanceRequestDTO.setRemarks(maintenanceRequest.getRemarks());
        return maintenanceRequestDTO;
    }

    public Long getRequestId() {
        return requestId;
    }

    public void setRequestId(Long requestId) {
        this.requestId = requestId;
    }

    public Long getUnitId() {
        return unitId;
    }

    public void setUnitId(Long unitId) {
        this.unitId = unitId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Date getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(Date requestDate) {
        this.requestDate = requestDate;
    }

    public String getRequestDescription() {
        return requestDescription;
    }

    public void setRequestDescription(String requestDescription) {
        this.requestDescription = requestDescription;
    }

    public int getRequestStatus() {
        return requestStatus;
    }

    public void setRequestStatus(int requestStatus) {
        this.requestStatus = requestStatus;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getRemarks() {
        return remarks;
    }


    //convert to model from DTO

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    //convert to DTO from model

    public  MaintenanceRequest toModel() {
        MaintenanceRequest maintenanceRequest = new MaintenanceRequest();
        maintenanceRequest.setRequestId(this.getRequestId());
        maintenanceRequest.setRequestDate(this.getRequestDate());
        maintenanceRequest.setRequestDescription(this.getRequestDescription());
        maintenanceRequest.setRequestStatus(this.getRequestStatus());
        maintenanceRequest.setImg(this.getImg());
        maintenanceRequest.setRemarks(this.getRemarks());
        return maintenanceRequest;
    }
    
}
