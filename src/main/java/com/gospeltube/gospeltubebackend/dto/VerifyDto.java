package com.gospeltube.gospeltubebackend.dto;

public class VerifyDto {
    private String code;
    private String email;

    public VerifyDto(String code, String email) {
        this.code = code;
        this.email = email;
    }

    public VerifyDto() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
