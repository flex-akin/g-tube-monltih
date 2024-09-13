package com.gospeltube.gospeltubebackend.dto;

import java.time.LocalDateTime;

public interface VideoResponse {
    Long getId();
    String getTitle();
    String getDescription();
    String getCallId();
    String getVisibility();
    String getTags();
    boolean isComment();
    boolean isLiveNow();
    boolean isLive();
    Long getChurchId();
    String getVideoUrl();
    String getThumbnail();
    String getSeriesId();
    String getSeriesName();
    String getChurchName();
    String getLogo();
    String getStreamId();
    String getStreamIdExt();
    LocalDateTime getDate();
    Long getViews();
    boolean isLiked();
}
