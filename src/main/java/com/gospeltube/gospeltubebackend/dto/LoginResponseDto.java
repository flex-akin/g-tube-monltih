package com.gospeltube.gospeltubebackend.dto;

public class LoginResponseDto {
    private AppUserDto appUser;
    private String token;

    public LoginResponseDto(AppUserDto appUser, String token) {
        this.appUser = appUser;
        this.token = token;
    }

    public AppUserDto getAppUser() {
        return appUser;
    }

    public void setAppUser(AppUserDto appUser) {
        this.appUser = appUser;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
