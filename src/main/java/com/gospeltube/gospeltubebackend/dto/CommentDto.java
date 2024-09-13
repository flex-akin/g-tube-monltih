package com.gospeltube.gospeltubebackend.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class CommentDto {
    @NotEmpty(message = "comment is empty")
    private String comment;

    @NotNull(message = "blog not defined")
    private Long itemId;

    public CommentDto(String comment, Long itemId) {
        this.comment = comment;
        this.itemId = itemId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }


    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long blogId) {
        this.itemId = blogId;
    }
}
