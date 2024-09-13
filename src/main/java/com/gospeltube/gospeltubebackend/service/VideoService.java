package com.gospeltube.gospeltubebackend.service;

import com.gospeltube.gospeltubebackend.dto.*;
import com.gospeltube.gospeltubebackend.entity.Series;
import com.gospeltube.gospeltubebackend.entity.VideoComment;
import com.gospeltube.gospeltubebackend.entity.VideoLikes;
import com.gospeltube.gospeltubebackend.entity.Videos;

import java.util.Set;

public interface VideoService {
    Series createSeries(SeriesDto seriesDto, String email);
    Set<Series> getSeriesByChurch(String principal);
    Videos createVideo(VideoDto videoDto, String principal);
    Set<VideoResponse> getVideos(String isLive, String email);
    boolean afterLivestream(String videoUrl, Long videoId);
    Set<VideoResponse> getVideosByChurch(String isLive, Long ChurchId, String email);
    VideoComment createComment(CommentDto commentDto, String email);
    String createLikes(Long itemId, String email);
    boolean addToWatch(String email, Long videoId);
    Set<VideoLikes> getLikes(String email);
    Set<VideoCommentResponse> getVideoComments(Long videoId);
    boolean checkVideoLike(Long videoId, String email);
}