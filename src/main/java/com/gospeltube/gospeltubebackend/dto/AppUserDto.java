package com.gospeltube.gospeltubebackend.dto;


public class AppUserDto {
    private Long userId;
    private String phoneNumber;
    private String email;
    private String firstName;
    private String lastName;
    private String gender;
    private String streamId;
    private String streamIdExt;
    private String displayPicture;

    public AppUserDto(Long userId, String phoneNumber, String email, String firstName, String lastName, String gender, String streamId, String streamIdExt, String displayPicture) {
        this.userId = userId;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.streamId = streamId;
        this.streamIdExt = streamIdExt;
        this.displayPicture = displayPicture;
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

    public String getStreamId() {
        return streamId;
    }

    public void setStreamId(String streamId) {
        this.streamId = streamId;
    }

    public String getStreamIdExt() {
        return streamIdExt;
    }

    public void setStreamIdExt(String streamIdExt) {
        this.streamIdExt = streamIdExt;
    }

    public String getLogo() {
        return displayPicture;
    }

    public void setLogo(String displayPicture) {
        this.displayPicture = displayPicture;
    }
}
