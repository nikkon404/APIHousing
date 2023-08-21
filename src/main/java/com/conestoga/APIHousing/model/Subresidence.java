package com.conestoga.APIHousing.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.math.BigDecimal;
import javax.persistence.*;

@Entity
@Table(name = "subresidence") // Use the actual table name from the SQL definition
public class Subresidence {
    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "unit_id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "residence_id") // Map the ManyToOne relationship with the "residence_id" column
    @JsonIgnore // Add this annotation to break the cycle in JSON serialization
    private Residence residence;

    @Column(name = "unit_type") // Map the field to the "unit_type" column in the table
    private String unitType;

    @Column(name = "unit_description") // Map the field to the "unit_description" column in the table
    private String unitDescription;

    @Column(name = "monthly_rent") // Map the field to the "monthly_rent" column in the table
    private BigDecimal monthlyRent;

    @Column(name = "img") // Map the field to the "img" column in the table
    private String img;

    @Column(name = "occupied") // Map the field to the "occupied" column in the table
    private int occupied;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    // Getters and setters for all fields (unitId, residence, unitType, etc.)
    // ...

    // For brevity, I'm omitting the getters and setters.
    // You should add them based on your requirements.

    public Long getUnitId() {
        return id;
    }

    public void setUnitId(Long unitId) {
        this.id = unitId;
    }

    public Residence getResidence() {
        return residence;
    }

    public void setResidence(Residence residence) {
        this.residence = residence;
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

    public int getOccupied() {
        return occupied;
    }

    public void setOccupied(int occupied) {
        this.occupied = occupied;
    }
}

