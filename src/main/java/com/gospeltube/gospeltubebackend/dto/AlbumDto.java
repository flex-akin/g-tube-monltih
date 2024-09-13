package com.gospeltube.gospeltubebackend.dto;

import jakarta.validation.constraints.NotNull;

public class AlbumDto {

    @NotNull(message = "type cannot be empty")
    private String title;
    private String description;
    @NotNull(message = "type cannot be empty")
    private String artiste;
    private String imageUrl;
    private String date;
    public AlbumDto(String title, String description, String artiste, String imageUrl, String date) {
        this.title = title;
        this.description = description;
        this.artiste = artiste;
        this.imageUrl = imageUrl;
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getArtiste() {
        return artiste;
    }

    public void setArtiste(String artiste) {
        this.artiste = artiste;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
