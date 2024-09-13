package com.gospeltube.gospeltubebackend.dto;

import jakarta.validation.constraints.NotNull;

public class FeedbackDto {
    @NotNull(message = "comment should be provided")
    private String comment;

    public FeedbackDto(String comment) {
        this.comment = comment;
    }

    public @NotNull(message = "comment should be provided") String getComment() {
        return comment;
    }

    public void setComment(@NotNull(message = "comment should be provided") String comment) {
        this.comment = comment;
    }
}
