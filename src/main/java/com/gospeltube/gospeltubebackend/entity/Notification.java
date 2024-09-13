package com.gospeltube.gospeltubebackend.entity;

import jakarta.persistence.*;

@Entity
@Table(name="notification")
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String message;
    private String contentType;
    private String notificationType;
    @Column(name = "user_id")
    private Long userId;
    @Column(name = "item_id")
    private Long itemId;
    @ManyToOne
    @JoinColumn(name = "church_id")
    private Church church;

    public Notification(String message, String contentType, String notificationType, Long userId, Long itemId, Church church) {
        this.message = message;
        this.contentType = contentType;
        this.notificationType = notificationType;
        this.userId = userId;
        this.itemId = itemId;
        this.church = church;
    }

    public Notification() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(String notificationType) {
        this.notificationType = notificationType;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public Church getChurch() {
        return church;
    }

    public void setChurch(Church church) {
        this.church = church;
    }
}
