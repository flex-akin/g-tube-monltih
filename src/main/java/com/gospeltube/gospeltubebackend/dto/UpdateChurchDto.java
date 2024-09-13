package com.gospeltube.gospeltubebackend.dto;

public class UpdateChurchDto {
    private String churchName;
    private String logo;
    private String website;
    private String mission;
    private String bio;
    private String vision;
    private String instagram;
    private String twitter;
    private String facebook;

    public UpdateChurchDto(String churchName, String logo, String website, String mission, String bio, String vision, String instagram, String twitter, String facebook) {
        this.churchName = churchName;
        this.logo = logo;
        this.website = website;
        this.mission = mission;
        this.bio = bio;
        this.vision = vision;
        this.instagram = instagram;
        this.twitter = twitter;
        this.facebook = facebook;
    }

    public String getInstagram() {
        return instagram;
    }

    public void setInstagram(String instagram) {
        this.instagram = instagram;
    }

    public String getTwitter() {
        return twitter;
    }

    public void setTwitter(String twitter) {
        this.twitter = twitter;
    }

    public String getFacebook() {
        return facebook;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }

    public String getChurchName() {
        return churchName;
    }

    public void setChurchName(String churchName) {
        this.churchName = churchName;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getMission() {
        return mission;
    }

    public void setMission(String mission) {
        this.mission = mission;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getVision() {
        return vision;
    }

    public void setVision(String vision) {
        this.vision = vision;
    }
}
