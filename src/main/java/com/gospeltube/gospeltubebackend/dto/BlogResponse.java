package com.gospeltube.gospeltubebackend.dto;

import java.time.LocalDateTime;

public interface BlogResponse {
    Long getUserId();
    String getComment();
    String getFirstName();
    String getLastName();
    String getProfilePicture();
    LocalDateTime getTime();
    Long getLikes();
}
