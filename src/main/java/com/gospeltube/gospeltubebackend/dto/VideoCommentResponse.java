package com.gospeltube.gospeltubebackend.dto;

import java.time.LocalDateTime;

public interface VideoCommentResponse {
    Long getUserId();
    String getComment();
    String getFirstName();
    String getLastName();
    String getProfilePicture();
    LocalDateTime getDate();
    Long getLikes();
}
