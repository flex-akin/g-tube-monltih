package com.gospeltube.gospeltubebackend.dto;

public class ContinueWatchingDto {
    private String currentTime;
    private Long videoId;

    public ContinueWatchingDto(String currentTime, Long videoId) {
        this.currentTime = currentTime;
        this.videoId = videoId;
    }

    public ContinueWatchingDto() {
    }

    public String getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(String currentTime) {
        this.currentTime = currentTime;
    }

    public Long getVideoId() {
        return videoId;
    }

    public void setVideoId(Long videoId) {
        this.videoId = videoId;
    }
}

