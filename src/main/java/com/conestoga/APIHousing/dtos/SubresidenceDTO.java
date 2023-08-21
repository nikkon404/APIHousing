package com.conestoga.APIHousing.dtos;

import com.conestoga.APIHousing.model.Subresidence;
import java.math.BigDecimal;

public class SubresidenceDTO {

    private Long unitId;
    private Long residenceId;
    private String unitNumber;
    private String unitType;
    private String unitDescription;
    private BigDecimal monthlyRent;
    private String img;
        //dto from model

    //from model to dto
    public SubresidenceDTO(Subresidence unit) {
        this.unitId = unit.getUnitId();
        this.residenceId = unit.getResidence().getResidenceId();
        this.unitType = unit.getUnitType();
        this.unitDescription = unit.getUnitDescription();
        this.monthlyRent = unit.getMonthlyRent();
        this.img = unit.getImg();

    }

    public SubresidenceDTO() {

    }

    //to model
    public Subresidence convertToModel() {
        Subresidence unit = new Subresidence();
        unit.setUnitId(this.unitId);
        unit.setUnitType(this.unitType);
        unit.setUnitDescription(this.unitDescription);
        unit.setMonthlyRent(this.monthlyRent);
        unit.setImg(this.img);
        return unit;
    }

    public Long getUnitId() {
        return unitId;
    }

    public void setUnitId(Long unitId) {
        this.unitId = unitId;
    }

    public Long getResidenceId() {
        return residenceId;
    }

    public void setResidenceId(Long residenceId) {
        this.residenceId = residenceId;
    }

    public String getUnitNumber() {
        return unitNumber;
    }

    public void setUnitNumber(String unitNumber) {
        this.unitNumber = unitNumber;
    }

    public String getUnitType() {
        return unitType;
    }

    public void setUnitType(String unitType) {
        this.unitType = unitType;
    }

    public String getUnitDescription() {
        return unitDescription;
    }

    public void setUnitDescription(String unitDescription) {
        this.unitDescription = unitDescription;
    }

    public BigDecimal getMonthlyRent() {
        return monthlyRent;
    }

    public void setMonthlyRent(BigDecimal monthlyRent) {
        this.monthlyRent = monthlyRent;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}

