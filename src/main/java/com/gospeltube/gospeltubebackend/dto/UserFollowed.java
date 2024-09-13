package com.gospeltube.gospeltubebackend.dto;

public interface UserFollowed {
    String getChurchName();
    String getChurchId();
    String getDescription();
    String getProfilePicture();
    Long getTotalPosts();
    Long getTotatSubscribers();
    Long getTotalLikes();
    Long getTotalStreams();
    boolean isFollowing();
}
