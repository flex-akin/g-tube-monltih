package com.gospeltube.gospeltubebackend.dto;

public interface BlogResDto {
    Long getId();
    String getContent();
    String getImage();
    String getHeader();
    String getLink();
    String getTotalComments();
    String getTotalLikes();
    boolean isLiked();
    String getChurchId();
    String getDate();
    String getLogo();
    String getProfilePicture();
    String getChurchName();
}
