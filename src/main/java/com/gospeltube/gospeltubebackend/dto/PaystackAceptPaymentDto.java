package com.gospeltube.gospeltubebackend.dto;

public class PaystackAceptPaymentDto {
    private Long churchId;
    private String email;
    private String amount;

    public PaystackAceptPaymentDto(Long churchId, String email, String amount) {
        this.churchId = churchId;
        this.email = email;
        this.amount = amount;
    }

    public Long getChurchId() {
        return churchId;
    }

    public void setChurchId(Long churchId) {
        this.churchId = churchId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}



