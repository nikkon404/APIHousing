package com.conestoga.APIHousing.model;

import java.sql.Timestamp;
import javax.persistence.*;

@Entity
@Table(name = "notices")
public class Notice {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String title;

  @Column(nullable = false, columnDefinition = "TEXT")
  private String body;

  @Column(name = "img_url")
  private String imgUrl;

  @Column(name = "external_url")
  private String externalUrl;

  @Column(name = "is_pinned", columnDefinition = "INT DEFAULT 0")
  private int isPinned;

  // created at
  @Column(name = "created_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
  private Timestamp createdAt;

  // constructor    //getters and setters

  public Notice() {}

  public Notice(
      Long id,
      String title,
      String body,
      String imgUrl,
      String externalUrl,
      int isPinned,
      Timestamp createdAt) {
    this.id = id;
    this.title = title;
    this.body = body;
    this.imgUrl = imgUrl;
    this.externalUrl = externalUrl;
    this.isPinned = isPinned;
    this.createdAt = createdAt;
  }

  // getters and setters
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

  public String getBody() {
    return body;
  }

  public void setBody(String body) {
    this.body = body;
  }

  public String getImgUrl() {
    return imgUrl;
  }

  public void setImgUrl(String imgUrl) {
    this.imgUrl = imgUrl;
  }

  public String getExternalUrl() {
    return externalUrl;
  }

  public void setExternalUrl(String externalUrl) {
    this.externalUrl = externalUrl;
  }

  public int getIsPinned() {
    return isPinned;
  }

  public void setIsPinned(int isPinned) {
    this.isPinned = isPinned;
  }

  public Timestamp getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(Timestamp createdAt) {
    this.createdAt = createdAt;
  }
}
