package com.conestoga.APIHousing.model;

import com.conestoga.APIHousing.utils.Constants;
import java.util.Date;
import javax.persistence.*;

@Entity
@Table(name = "chat_messages")
public class ChatMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "sender_id")
    private String senderId;

    @Column(name = "sender_name")
    private String senderName;

    @Column(name = "content")
    private String content;

    @Column(name = "content_type")
    private String contentType;

    @Column(name = "date")
    private Date date;

    public ChatMessage() {
    }

    public ChatMessage(String senderId, String senderName, String content, String contentType, Date date) {
        this.senderId = senderId;
        this.senderName = senderName;
        this.content = content;
        this.contentType = contentType;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

     public String getBriefMsg() {
        //if content type = "txt" then return content else return "sent an image"
        if(contentType.equals(Constants.CONTENT_TEXT)){
            //if length is greater than 50 then return substring of 50 characters ad add ... at the end
            if(content.length() > 50){
                return content.substring(0, 50) + "...";
            }
            return content;
        }else{
            return "sent an image";
        }
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
