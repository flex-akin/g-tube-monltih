package com.gospeltube.gospeltubebackend.dto;

public class LoginDto {

    private String email;
    private String password;
    private boolean isOauth;

    public LoginDto(String email, String password, boolean isOauth) {
        this.email = email;
        this.password = password;
        this.isOauth = isOauth;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isOauth() {
        return isOauth;
    }

    public void setOauth(boolean oauth) {
        isOauth = oauth;
    }
}
