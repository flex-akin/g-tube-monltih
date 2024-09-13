package com.gospeltube.gospeltubebackend.dto;

public class NotificationDto {
    private Long userId;
    private Long itemId;
    private Long churchId;
    private String contentType;
    private String notificationType;

    public NotificationDto(Long userId, Long itemId, Long churchId, String contentType, String notificationType) {
        this.userId = userId;
        this.itemId = itemId;
        this.churchId = churchId;
        this.contentType = contentType;
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

    public Long getChurchId() {
        return churchId;
    }

    public void setChurchId(Long churchId) {
        this.churchId = churchId;
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
}
