package com.gospeltube.gospeltubebackend.dto;

import jakarta.validation.constraints.NotNull;

public class PasswordDto {
    @NotNull
    private String password;
    @NotNull
    private String oldPassword;

    public PasswordDto(String password, String oldPassword) {
        this.password = password;
        this.oldPassword = oldPassword;
    }

    public @NotNull String getPassword() {
        return password;
    }

    public void setPassword(@NotNull String password) {
        this.password = password;
    }

    public @NotNull String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(@NotNull String oldPassword) {
        this.oldPassword = oldPassword;
    }
}
