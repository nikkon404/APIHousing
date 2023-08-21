package com.conestoga.APIHousing.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;

public class AccountDTO {
    private Long id;
    private String stripeCustomerId;
    private String email;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String address;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOfBirth;


    private String fcm;
    private String collegeName;
    private String studentId;
    private String postalCode;
    private int role;
    private String img;
    private String password;

    public AccountDTO() {
    }

    public AccountDTO(Long id,  String email, String firstName, String lastName,
                      String phoneNumber, String address, LocalDate dateOfBirth, String fcm, String collegeName, String studentId, String postalCode, int role,
                      String img, String password
    ) {
        this.id = id;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.dateOfBirth = dateOfBirth;
        this.fcm = fcm;
        this.collegeName = collegeName;
        this.studentId = studentId;
        this.postalCode = postalCode;
        this.role = role;
        this.img = img;
        this.password = password;

    }

    public AccountDTO(String email, String firstName, String lastName,
                      String phoneNumber, String address, LocalDate dateOfBirth, String fcm, String collegeName, String studentId, String postalCode,
                      int role, String img, String password
    ) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.dateOfBirth = dateOfBirth;
        this.fcm = fcm;
        this.collegeName = collegeName;
        this.studentId = studentId;
        this.postalCode = postalCode;
        this.role = role;
        this.img = img;
        this.password = password;
    }

    // Getters and Setters
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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getFcm() {
        return fcm;
    }

    public void setFcm(String fcm) {
        this.fcm = fcm;
    }

    public String getCollegeName() {
        return collegeName;
    }

    public void setCollegeName(String collegeName) {
        this.collegeName = collegeName;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getStripeCustomerId() {
        return stripeCustomerId;
    }

    public void setStripeCustomerId(String stripeCustomerId) {
        this.stripeCustomerId = stripeCustomerId;
    }

    
}
