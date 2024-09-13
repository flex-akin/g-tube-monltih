package com.gospeltube.gospeltubebackend.dto;

import java.util.Objects;

public class PaystackSubaccountResponseDto {
    private String message;
    private String status;
    private SubacctountData data;

    public PaystackSubaccountResponseDto(String message, String status, SubacctountData data) {
        this.message = message;
        this.status = status;
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public String getStatus() {
        return status;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public SubacctountData getData() {
        return data;
    }

    public void setData(SubacctountData data) {
        this.data = data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PaystackSubaccountResponseDto that = (PaystackSubaccountResponseDto) o;
        return Objects.equals(message, that.message) && Objects.equals(status, that.status) && Objects.equals(data, that.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(message, status, data);
    }
}
