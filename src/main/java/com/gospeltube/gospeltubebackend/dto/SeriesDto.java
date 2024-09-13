package com.gospeltube.gospeltubebackend.dto;

import com.gospeltube.gospeltubebackend.entity.Videos;
import com.gospeltube.gospeltubebackend.enums.Visibility;
import jakarta.validation.constraints.NotNull;


import java.util.Set;

public class SeriesDto {

    @NotNull(message = "title cannot be empty")
    private String title;
    private String description;
    private String tags;
    @NotNull(message = "language is not set")
    private String language;
    @NotNull(message = "visibility cannot be empty")
    private Visibility visibility;

    public SeriesDto(String title, String description, String language, Visibility visibility, String tags) {
        this.title = title;
        this.description = description;
        this.language = language;
        this.visibility = visibility;
        this.tags = tags;
    }

    public SeriesDto() {
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
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

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Visibility getVisibility() {
        return visibility;
    }

    public void setVisibility(Visibility visibility) {
        this.visibility = visibility;
    }
}
