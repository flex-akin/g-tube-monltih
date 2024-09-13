package com.gospeltube.gospeltubebackend.dto;

import jakarta.validation.constraints.NotNull;

public class AdminNotificationDto {
    @NotNull(message = "notification message must be provided")
    private String message;
    private String title;
    private String recipient;

    public AdminNotificationDto(String message, String title, String recipent) {
        this.message = message;
        this.title = title;
        this.recipient = recipent;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }
}
