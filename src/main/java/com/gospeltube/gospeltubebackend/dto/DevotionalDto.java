package com.gospeltube.gospeltubebackend.dto;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public class DevotionalDto {
    @NotNull
    private String header;
    @NotNull
    private String content;
    @NotNull
    private String bibleRef;
    private LocalDateTime date;
    private boolean isBookmarked;
    private Long churchId;
    private Long id;
    public DevotionalDto() {}

    public DevotionalDto(String header, String content, LocalDateTime date, String bibleRef) {
        this.header = header;
        this.content = content;
        this.date = date;
        this.bibleRef = bibleRef;
    }

    public Long getChurchId() {
        return churchId;
    }

    public void setChurchId(Long churchId) {
        this.churchId = churchId;
    }

    public boolean isBookmarked() {
        return isBookmarked;
    }
    public void setBookmarked(boolean bookmarked) {
        isBookmarked = bookmarked;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getBibleRef() {
        return bibleRef;
    }

    public void setBibleRef(String bibleRef) {
        this.bibleRef = bibleRef;
    }
}
