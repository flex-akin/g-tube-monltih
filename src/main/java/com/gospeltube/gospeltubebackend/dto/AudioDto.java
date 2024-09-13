package com.gospeltube.gospeltubebackend.dto;

import com.gospeltube.gospeltubebackend.enums.AudioType;
import com.gospeltube.gospeltubebackend.enums.Visibility;
import jakarta.validation.constraints.NotNull;



public class AudioDto {

    @NotNull(message = "type cannot be empty")
    private AudioType type;
    @NotNull(message = "audio content must be uploaded")
    private String audioUrl;
    private String imageUrl;
    @NotNull(message = "title cannot be empty")
    private String title;
    private String artiste;
    private String featuring;
    @NotNull(message = "visibility cannot be empty")
    private Visibility visibility;
    private String date;
    private Long album;
    private String duration;

    public AudioDto(AudioType type, String audioUrl, String imageUrl, String title, String artiste, String featuring, Visibility visibility, String date, Long album, String duration) {
        this.type = type;
        this.audioUrl = audioUrl;
        this.imageUrl = imageUrl;
        this.title = title;
        this.artiste = artiste;
        this.featuring = featuring;
        this.visibility = visibility;
        this.date = date;
        this.album = album;
        this.duration = duration;
    }

    public @NotNull(message = "type cannot be empty") AudioType getType() {
        return type;
    }

    public void setType(@NotNull(message = "type cannot be empty") AudioType type) {
        this.type = type;
    }

    public @NotNull(message = "audio content must be uploaded") String getAudioUrl() {
        return audioUrl;
    }

    public void setAudioUrl(@NotNull(message = "audio content must be uploaded") String audioUrl) {
        this.audioUrl = audioUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public @NotNull(message = "title cannot be empty") String getTitle() {
        return title;
    }

    public void setTitle(@NotNull(message = "title cannot be empty") String title) {
        this.title = title;
    }

    public String getArtiste() {
        return artiste;
    }

    public void setArtiste(String artiste) {
        this.artiste = artiste;
    }

    public String getFeaturing() {
        return featuring;
    }

    public void setFeaturing(String featuring) {
        this.featuring = featuring;
    }

    public @NotNull(message = "visibility cannot be empty") Visibility getVisibility() {
        return visibility;
    }

    public void setVisibility(@NotNull(message = "visibility cannot be empty") Visibility visibility) {
        this.visibility = visibility;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Long getAlbum() {
        return album;
    }

    public void setAlbum(Long album) {
        this.album = album;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }
}
