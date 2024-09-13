package com.gospeltube.gospeltubebackend.dto;


import jakarta.validation.constraints.NotNull;

public class BlogDto {
    @NotNull(message = "header cannot be empty")
    private String header;
    @NotNull(message = "image cannot be empty")
    private String image;
    private String link;
    @NotNull(message = "content cannot be empty")
    private String content;

    public BlogDto(String header, String image, String link, String content) {
        this.header = header;
        this.image = image;
        this.link = link;
        this.content = content;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
