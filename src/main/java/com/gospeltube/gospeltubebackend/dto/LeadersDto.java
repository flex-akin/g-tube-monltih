package com.gospeltube.gospeltubebackend.dto;

public class LeadersDto {
    private String image;
    private String bio;
    private String name;

    public LeadersDto(String image, String bio, String name) {
        this.image = image;
        this.bio = bio;
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
