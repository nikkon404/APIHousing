// package com.conestoga.APIHousing.dtos;

// import javax.persistence.*;
// import java.time.LocalDateTime;

// @Entity
// @Table(name = "pin")
// public class PinDTO {

//     @Id
//     @GeneratedValue(strategy = GenerationType.IDENTITY)
//     private Long id;

//     @Column(nullable = false)
//     private String email;

//     @Column(nullable = false)
//     private String pinCode;

//     @Column(nullable = false)
//     private LocalDateTime createdAt;

//     // Constructors, getters, setters, etc.
//     public PinDTO() {
//     }

//     public PinDTO(String email, String pinCode, LocalDateTime createdAt) {
//         this.email = email;
//         this.pinCode = pinCode;
//         this.createdAt = createdAt;
//     }

//     public String getEmail() {
//         return email;
//     }

//     public void setEmail(String email) {
//         this.email = email;
//     }

//     public String getPinCode() {
//         return pinCode;
//     }

//     public void setPinCode(String pinCode) {
//         this.pinCode = pinCode;
//     }

//     public LocalDateTime getCreatedAt() {
//         return createdAt;
//     }

//     public void setCreatedAt(LocalDateTime createdAt) {
//         this.createdAt = createdAt;
//     }
// }

