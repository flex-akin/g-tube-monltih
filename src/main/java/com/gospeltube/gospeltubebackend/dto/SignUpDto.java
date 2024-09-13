package com.gospeltube.gospeltubebackend.dto;

import jakarta.validation.constraints.NotNull;


public class SignUpDto {
    private Long userId;
    private String phoneNumber;
    @NotNull(message = "Email must not be empty")
    private String email;
    private String password;
    @NotNull(message = "first name must not be empty")
    private String firstName;
    @NotNull(message = "first name must not be empty")
    private String lastName;
    private String gender;
    private String profilePicture;
    private boolean isOauth2;

    public SignUpDto( String phoneNumber, String email, String password, String firstName, String lastName, String gender,
    String profilePicture, boolean isOauth2) {

        this.phoneNumber = phoneNumber;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.profilePicture = profilePicture;
        this.isOauth2 = isOauth2;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public boolean isOauth2() {
        return isOauth2;
    }

    public void setOauth2(boolean oauth2) {
        isOauth2 = oauth2;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }
}
