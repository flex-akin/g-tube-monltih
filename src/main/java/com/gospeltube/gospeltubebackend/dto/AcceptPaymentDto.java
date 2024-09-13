package com.gospeltube.gospeltubebackend.dto;


public class AcceptPaymentDto {
    private String status;
    private String message;
    private PaymentResponseData data;


    public AcceptPaymentDto(String status, String message, PaymentResponseData data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public PaymentResponseData getData() {
        return data;
    }

    public void setPaymentResponseData(PaymentResponseData data) {
        this.data = data;
    }
}

