package com.conestoga.APIHousing.model;

public class NotificationRequest {
    private String title;
    private String description;
    private String image;

    // Getters and setters

    public NotificationRequest(String title, String description, String image) {
        this.title = title;
        this.description = description;
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    //getter for image
    public String getImage() {
        return image;
    }

    //setter for image
    public void setImage(String image) {
        this.image = image;
    }
}
