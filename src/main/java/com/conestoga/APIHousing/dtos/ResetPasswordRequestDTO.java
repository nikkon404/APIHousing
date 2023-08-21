package com.conestoga.APIHousing.dtos;

// Create a separate class to represent the request body for the password reset API
public class ResetPasswordRequestDTO {
    private String email;
    private String resetToken;
    private String newPassword;

    // constructors, getters, setters

    public ResetPasswordRequestDTO() {
    }

    public ResetPasswordRequestDTO(String email, String resetToken, String newPassword) {
        this.email = email;
        this.resetToken = resetToken;
        this.newPassword = newPassword;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getResetToken() {
        return resetToken;
    }

    public void setResetToken(String resetToken) {
        this.resetToken = resetToken;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
