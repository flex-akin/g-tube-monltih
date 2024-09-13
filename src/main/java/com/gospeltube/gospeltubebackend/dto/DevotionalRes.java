package com.gospeltube.gospeltubebackend.dto;

public interface DevotionalRes {
    String getContent();
    String getHeader();
    String getTime();
    String getChurchId();
    String getBibleRef();
    boolean getBookmarked();
    Long getId();
}
