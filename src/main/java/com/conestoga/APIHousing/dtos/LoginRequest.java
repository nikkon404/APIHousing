package com.conestoga.APIHousing.dtos;

public class LoginRequest {
  private String email;
  private String password;
  private String fcm;

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getFcm() {
    return fcm;
  }

  public void setFcm(String fcmToken) {
    this.fcm = fcmToken;
  }

  @Override
  public String toString() {
    return "LoginRequest [password=" + password + ", email=" + email + "]";
  }
}
