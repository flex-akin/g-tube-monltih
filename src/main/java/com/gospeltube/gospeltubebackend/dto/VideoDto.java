package com.gospeltube.gospeltubebackend.dto;

import com.gospeltube.gospeltubebackend.enums.Visibility;
import jakarta.validation.constraints.NotNull;


public class VideoDto {
    @NotNull(message = "title cannot be empty")
    private String title;
    private String description;
    private String callId;
    @NotNull(message = "visibility cannot be empty")
    private Visibility visibility;
    private String tags;
    private boolean comment;
    private String time;
    @NotNull(message = "video url cannot be empty")
    private String videoUrl;
    private String thumbnail;
    private boolean live;
    private boolean liveNow;
    private Long series;


    public VideoDto(String title, String description, String callId, Visibility visibility, String tags, boolean comment, String time, String videoUrl, String thumbnail, boolean live, Long series, boolean liveNow) {
        this.title = title;
        this.description = description;
        this.callId = callId;
        this.visibility = visibility;
        this.tags = tags;
        this.comment = comment;
        this.time = time;
        this.videoUrl = videoUrl;
        this.thumbnail = thumbnail;
        this.live = live;
        this.liveNow = liveNow;
        this.series = series;
    }

    public VideoDto() {
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

    public String getCallId() {
        return callId;
    }

    public void setCallId(String callId) {
        this.callId = callId;
    }

    public Visibility getVisibility() {
        return visibility;
    }

    public void setVisibility(Visibility visibility) {
        this.visibility = visibility;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public boolean isComment() {
        return comment;
    }

    public void setComment(boolean comment) {
        this.comment = comment;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public boolean isLive() {
        return live;
    }

    public void setLive(boolean live) {
        this.live = live;
    }

    public Long getSeries() {
        return series;
    }

    public void setSeries(Long series) {
        this.series = series;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public boolean isLiveNow() {
        return liveNow;
    }

    public void setLiveNow(boolean liveNow) {
        this.liveNow = liveNow;
    }
}

