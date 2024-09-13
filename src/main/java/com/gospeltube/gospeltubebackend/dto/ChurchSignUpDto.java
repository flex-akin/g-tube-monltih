package com.gospeltube.gospeltubebackend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public class ChurchSignUpDto {
    private Long userId;
    @NotNull(message = "Email must not be empty")
    @Email(message = "email is not a valid email")
    private String email;
    private String password;
    @NotNull(message = "first name must not be empty")
    private String firstName;
    private String lastName;
    @NotNull(message = "description must not be empty")
    private String description;
    @NotNull(message = "website must not be empty")
    private String website;
    @NotNull(message = "address must not be empty")
    private String address;
    @NotNull(message = "province must not be empty")
    private String province;
    @NotNull(message = "city must not be empty")
    private String city;
    @NotNull(message = "country must not be empty")
    private String country;
    @NotNull(message = "zip must not be empty")
    private String zip;
    private String doc;
    private String logo;
    private boolean isOauth2;

    public ChurchSignUpDto(Long userId, String email, String password, String firstName, String lastName, String description, String website, String address, String province, String city, String country, String zip,
                           String doc, String logo, boolean isOauth2) {
        this.userId = userId;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.description = description;
        this.website = website;
        this.address = address;
        this.province = province;
        this.city = city;
        this.country = country;
        this.zip = zip;
        this.doc = doc;
        this.logo = logo;
        this.isOauth2 = isOauth2;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getDoc() {
        return doc;
    }

    public void setDoc(String doc) {
        this.doc = doc;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public boolean isOauth2() {
        return isOauth2;
    }

    public void setOauth2(boolean oauth2) {
        isOauth2 = oauth2;
    }
}
