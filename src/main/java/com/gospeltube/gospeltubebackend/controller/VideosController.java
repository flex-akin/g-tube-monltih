package com.gospeltube.gospeltubebackend.controller;


import com.gospeltube.gospeltubebackend.dto.*;
import com.gospeltube.gospeltubebackend.entity.Series;
import com.gospeltube.gospeltubebackend.entity.VideoLikes;
import com.gospeltube.gospeltubebackend.entity.Videos;
import com.gospeltube.gospeltubebackend.exception.BadRequestException;
import com.gospeltube.gospeltubebackend.exception.ResourceNotFoundException;
import com.gospeltube.gospeltubebackend.service.VideoService;
import com.gospeltube.gospeltubebackend.util.Utilities;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;



@RestController
@Validated
@CrossOrigin("*")
@RequestMapping("/api/v1/videos")
public class VideosController {
    private final VideoService videoService;
    private final JwtDecoder jwtDecoder;

    public VideosController(VideoService videoService, JwtDecoder jwtDecoder) {
        this.videoService = videoService;
        this.jwtDecoder = jwtDecoder;
    }

    @PostMapping("/createSeries")
    public ResponseEntity<Response> createSeries(@Valid @RequestBody SeriesDto seriesDto,
                                                 @RequestHeader(name = "Authorization") String authorizationHeader){
        Utilities utilities = new Utilities(jwtDecoder);
        String principal = utilities.getPrincipal(authorizationHeader);
        Series savedSeries = this.videoService.createSeries(seriesDto, principal);
        Set<Series> savedSeriesSet= new HashSet<>();
        savedSeriesSet.add(savedSeries);
        return new ResponseEntity<>(new Response("00", "series created successfully", true, savedSeriesSet ),HttpStatus.CREATED);
    }

    @GetMapping("/series")
    public ResponseEntity<Response> getSeries(
            @RequestHeader(name = "Authorization") String authorizationHeader
    ){
        Utilities utilities = new Utilities(jwtDecoder);
        String principal = utilities.getPrincipal(authorizationHeader);
        Set<Series> series = this.videoService.getSeriesByChurch(principal);
        return new ResponseEntity<>(new Response("00", "series fetched", true, series ),HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<Response> createVideo(
            @Valid @RequestBody VideoDto videoDto,
            @RequestHeader(name = "Authorization") String authorizationHeader){
        Utilities utilities = new Utilities(jwtDecoder);
        String principal = utilities.getPrincipal(authorizationHeader);
        Videos video = this.videoService.createVideo(videoDto, principal);
        Set<Videos> savedVideo= new HashSet<>();
        savedVideo.add(video);
        return new ResponseEntity<>(new Response("00", "video created", true, savedVideo ),HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<Response> getVideos(
            @RequestParam("live") Optional<String> live,
     @RequestHeader(name = "Authorization") String authorizationHeader){
        Utilities utilities = new Utilities(jwtDecoder);
        String principal = utilities.getPrincipal(authorizationHeader);
        String code;
        code = live.orElse("2");
        Set<VideoResponse> videos = this.videoService.getVideos(code, principal);
        return new ResponseEntity<>(new Response("00", "videos fetch successful", true, videos ),HttpStatus.OK);
    }

    @PutMapping("/EndLiveStream")
    public ResponseEntity<Response> emdLiveStream( @Valid @RequestBody EndLiveDto endLiveDto ){
        boolean value = this.videoService.afterLivestream(endLiveDto.getVideoUrl(), endLiveDto.getVideoId());
        return new ResponseEntity<>(new Response("00", "videos updated successfully", value, null ),HttpStatus.OK);

    }

    @GetMapping("/churchVideos/{churchId}")
    public ResponseEntity<Response> getChurchVideos(
            @RequestParam("live") Optional<String> live,
            @PathVariable String churchId,
            @RequestHeader(name = "Authorization") String authorizationHeader
    ){
        Utilities utilities = new Utilities(jwtDecoder);
        String principal = utilities.getPrincipal(authorizationHeader);
        String isLive;
        if (live.isEmpty()){
            isLive = "0";
        }else {
            isLive = live.get();
        }
        Long id = Long.parseLong(churchId);
        Set<VideoResponse> videos = this.videoService.getVideosByChurch(isLive, id, principal);
        return new ResponseEntity<>(new Response(
                "00", "data fetch successful", true, videos
        ), HttpStatus.OK);
    }

    @PostMapping("/comment")
    public ResponseEntity<Response> createComment(
            @RequestBody CommentDto commentDto,
            @RequestHeader(name = "Authorization") String authorizationHeader

    ){
        Utilities utilities = new Utilities(jwtDecoder);
        String principal = utilities.getPrincipal(authorizationHeader);
        this.videoService.createComment(commentDto, principal);
        return new ResponseEntity<>(new Response(
                "00", "comment", true, null
        ), HttpStatus.OK);
    }

    @PostMapping("/likes")
    public ResponseEntity<Response> likeVideo(
            @RequestParam("videoId") Optional<Long> videoId,
            @RequestHeader(name = "Authorization") String authorizationHeader
    )
    {
        Utilities utilities = new Utilities(jwtDecoder);
        String email = utilities.getPrincipal(authorizationHeader);
        Long video;
        video = videoId.orElseThrow(
                () -> new ResourceNotFoundException("video not defined")
        );
        String message = this.videoService.createLikes(video, email);
        return new ResponseEntity<>(new Response(
                "00", message, true, null
        ), HttpStatus.OK);
    }

    @PostMapping("/watch")
    public ResponseEntity<Response> addToWatch(
            @RequestParam("videoId") Optional<Long> videoId,
            @RequestHeader(name = "Authorization") String authorizationHeader
    ){
        Utilities utilities = new Utilities(jwtDecoder);
        String email = utilities.getPrincipal(authorizationHeader);
        Long video;
        video = videoId.orElseThrow(
                () -> new ResourceNotFoundException("video not defined")
        );
        this.videoService.addToWatch(email, video);
        return new ResponseEntity<>(new Response(
                "00", "successfully added to watch", true, null
        ), HttpStatus.OK);
    }

    @GetMapping("/getLiked")
    public ResponseEntity<Response> getLikes(
            @RequestHeader(name = "Authorization") String authorizationHeader
    ){ Utilities utilities = new Utilities(jwtDecoder);
        String email = utilities.getPrincipal(authorizationHeader);
        Set<VideoLikes> videos = this.videoService.getLikes(email);
        return new ResponseEntity<>(new Response(
                "00", "data fetched successfully", true, videos
        ), HttpStatus.OK);
    }

    @GetMapping("/getVideoComments")
    public ResponseEntity<Response> getComments(
            @RequestParam("videoId") Optional<Long> videoId
    ){
        Long video = videoId.orElseThrow(
                () -> new BadRequestException("video not defined")
        );
       Set<VideoCommentResponse>  videoCommentResponses = this.videoService.getVideoComments(video);
       return new ResponseEntity<>(
               new Response(
                       "00", "data fetched successfully", true,videoCommentResponses
               ), HttpStatus.OK
       );
    }
//
//    @GetMapping("/checkVideoLike/{videoId}")
//    public ResponseEntity<Response> checkVideoLike

}
