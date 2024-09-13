package com.gospeltube.gospeltubebackend.dto;

public record AnalyticsResDto(
        Long totalLikes,
        Long totalSubscribers,
        Long totalStreams
) {
}
