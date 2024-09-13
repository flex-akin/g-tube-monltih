package com.gospeltube.gospeltubebackend.dto;

public class EndLiveDto {
    private Long videoId;
    private String videoUrl;

    public EndLiveDto(Long videoId, String videoUrl) {
        this.videoId = videoId;
        this.videoUrl = videoUrl;
    }

    public Long getVideoId() {
        return videoId;
    }

    public void setVideoId(Long videoId) {
        this.videoId = videoId;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }
}
