package com.conestoga.APIHousing.model;

import java.util.Date;
import javax.persistence.*;

@Entity
@Table(name = "pin")
public class Pin {

//fields : id email, pinCode, createdAt

//generate fields
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
@Column(name = "id")
private Long id;

@Column(name = "email")
private String email;

@Column(name = "pin_code")
private String pinCode;

@Column(name = "created_at")
private Date createdAt;

//generate constructor
public Pin() {}

public Pin(String email, String pinCode) {
    this.email = email;
    this.pinCode = pinCode;
    this.createdAt = new Date();
}

//generate getters and setters

public Long getId() {
    return id;
}

public void setId(Long id) {
    this.id = id;
}

public String getEmail() {
    return email;
}

public void setEmail(String email) {
    this.email = email;
}

public String getPinCode() {
    return pinCode;}

public void setPinCode(String pinCode) {
    this.pinCode = pinCode;
}

public Date getCreatedAt() {
    return createdAt;
}

public void setCreatedAt(Date createdAt) {
    this.createdAt = createdAt;
}



    
    
}
