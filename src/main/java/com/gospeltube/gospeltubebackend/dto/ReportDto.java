package com.gospeltube.gospeltubebackend.dto;

import jakarta.validation.constraints.NotNull;

public class ReportDto {
    @NotNull(message = "item id must be provided")
    private Long item_Id;
    @NotNull(message = "message must be provided")
    private String message;
    @NotNull(message = "type of item must be provided")
    private String itemType;

    public ReportDto(Long item_Id, String message, String itemType) {
        this.item_Id = item_Id;
        this.message = message;
        this.itemType = itemType;
    }

    public @NotNull(message = "item id must be provided") Long getItem_Id() {
        return item_Id;
    }

    public void setItem_Id(@NotNull(message = "item id must be provided") Long item_Id) {
        this.item_Id = item_Id;
    }

    public @NotNull(message = "message must be provided") String getMessage() {
        return message;
    }

    public void setMessage(@NotNull(message = "message must be provided") String message) {
        this.message = message;
    }

    public @NotNull(message = "type of item must be provided") String getItemType() {
        return itemType;
    }

    public void setItemType(@NotNull(message = "type of item must be provided") String itemType) {
        this.itemType = itemType;
    }
}
