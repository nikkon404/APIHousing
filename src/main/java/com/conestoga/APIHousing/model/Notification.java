package com.conestoga.APIHousing.model;

import java.util.Date;
import javax.persistence.*;

@Entity
@Table(name = "notifications")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "type", nullable = false)
    private int type;

    @Column(name = "created_at", updatable = false, insertable = false)
    private Date createdAt;

    public Notification(String title, Long userId, int type) {
        this.title = title;
        this.userId = userId;
        this.type = type;
        this.createdAt = new Date();
    }

    public Notification() {
        this.createdAt = new Date();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

   

}
