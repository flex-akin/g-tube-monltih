package com.gospeltube.gospeltubebackend.dto;

public class StripeAccountDto {
    private String businessType;
    private String country;
    private String email;

    public StripeAccountDto(String businessType, String country, String email) {
        this.businessType = businessType;
        this.country = country;
        this.email = email;
    }

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
