package com.gospeltube.gospeltubebackend.service.impl;

import com.gospeltube.gospeltubebackend.dto.*;
import com.gospeltube.gospeltubebackend.entity.*;
import com.gospeltube.gospeltubebackend.exception.BadRequestException;
import com.gospeltube.gospeltubebackend.exception.ResourceNotFoundException;
import com.gospeltube.gospeltubebackend.repository.*;
import com.gospeltube.gospeltubebackend.service.VideoService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
public class VideoServiceImpl implements VideoService {

    private final SeriesRepository seriesRepository;
    private final UserRepository userRepository;
    private final ChurchRepository churchRepository;
    private final VideoRepository videoRepository;
    private final VideoCommentsRepository videoCommentsRepository;
    private final VideoLikesRepository videoLIkesRepository;
    private final UserService userService;

    public VideoServiceImpl(SeriesRepository seriesRepository, UserRepository userRepository, ChurchRepository churchRepository, VideoRepository videoRepository, VideoCommentsRepository videoCommentsRepository, VideoLikesRepository videoLIkesRepository, UserService userService) {
        this.seriesRepository = seriesRepository;
        this.userRepository = userRepository;
        this.churchRepository = churchRepository;
        this.videoRepository = videoRepository;
        this.videoCommentsRepository = videoCommentsRepository;
        this.videoLIkesRepository = videoLIkesRepository;
        this.userService = userService;
    }
    @Override
    public Series createSeries(SeriesDto seriesDto, String email) {
        AppUser user = this.userRepository.findByEmail(email).orElseThrow(
                () -> new ResourceNotFoundException("user not found")
        );
        Church church = this.churchRepository.findById(user.getChurch().getId()).orElseThrow(
                () -> new ResourceNotFoundException("church doesn't exit")
        );
        return this.seriesRepository.save(new Series(
                seriesDto.getTitle(),
                seriesDto.getDescription(),
                seriesDto.getTags(),
                seriesDto.getLanguage(),
                seriesDto.getVisibility(),
                church
        ));
    }

    public Set<Series> getSeriesByChurch(String email){

        AppUser user = this.userRepository.findByEmail(email).orElseThrow(
                () -> new ResourceNotFoundException("user not found")
        );
        Church church = this.churchRepository.findById(user.getChurch().getId()).orElseThrow(
                () -> new ResourceNotFoundException("church doesn't exit")
        );
        return this.seriesRepository.findByChurch(church);
    }

    public Videos createVideo(VideoDto videoDto, String email){
        AppUser user = this.userRepository.findByEmail(email).orElseThrow(
                () -> new ResourceNotFoundException("user not found")
        );
        Church church = this.churchRepository.findById(user.getChurch().getId()).orElseThrow(
                () -> new ResourceNotFoundException("church doesn't exit")
        );

        Series series;
        if (videoDto.getSeries() != null){
            series = this.seriesRepository.findById(videoDto.getSeries()).orElseThrow(
                    () ->  new ResourceNotFoundException("series doesn't exist for this church")
            );
        }
        else {
            series = null;
        }

        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        LocalDateTime dateTime = LocalDateTime.parse(videoDto.getTime(), formatter);

        if (videoDto.isLive() && videoDto.getCallId() == null){
            throw new BadRequestException("no call id attached for this live video");
        }
        else if (!videoDto.isLive() && videoDto.getVideoUrl() == null){
            throw new BadRequestException("no stream link attached");
        }
        user.setTotalPosts(user.getTotalPosts() + 1L);
        this.userRepository.save(user);
        return this.videoRepository.save(new Videos(
                videoDto.getTitle(),
                videoDto.getDescription(),
                videoDto.getCallId(),
                videoDto.getVisibility(),
                videoDto.getTags(),
                videoDto.isComment(),
                dateTime,
                videoDto.isLive(),
                videoDto.isLiveNow(),
                videoDto.getVideoUrl(),
                videoDto.getThumbnail(),
                series,
                church
        ));
    }

    @Override
    public Set<VideoResponse> getVideos(String isLive, String email) {
        AppUser user = this.userRepository.findByEmail(email).orElseThrow(
                () -> new ResourceNotFoundException("user not found")
        );
        boolean live = false;
        if (Objects.equals(isLive, "1")){
            live = true;
        }
        else if (Objects.equals(isLive, "2")) {
            return this.videoRepository.getAllVideos(user.getUserId());
        }
        return this.videoRepository.getVideosByLive(live, user.getUserId());
    }

    @Override
    public boolean afterLivestream(String videoUrl, Long videoId) {
        Videos video = this.videoRepository.findById(videoId).orElseThrow(
                () -> new ResourceNotFoundException("video not found")
        );
        video.setLive(false);
        video.setLiveNow(false);
        video.setVideoUrl(videoUrl);
        this.videoRepository.save(video);
        return true;
    }

    public Set<VideoResponse> getVideosByChurch(String isLive, Long churchId, String email ) {
        AppUser user = this.userRepository.findById(churchId).orElseThrow(
                () -> new ResourceNotFoundException("user not found")
        );

        AppUser appUser = this.userRepository.findByEmail(email).orElseThrow(
                () -> new ResourceNotFoundException("user not found")
        );

        boolean live = false;
        if (Objects.equals(isLive, "1")){
            live = true;
        }
        return this.videoRepository.getVideosByChurchLive(live, user.getChurch().getId(), appUser.getUserId());
    }

    @Override
    public VideoComment createComment(CommentDto commentDto, String email) {
        AppUser user = this.userRepository.findByEmail(email).orElseThrow(
                () -> new ResourceNotFoundException("user not found")
        );
        Videos video = this.videoRepository.findById(commentDto.getItemId()).orElseThrow(
                () -> new ResourceNotFoundException("video not found")
        );

        AppUser churchUser = this.userRepository.findByChurchId(video.getChurch().getId()).orElseThrow(
                () -> new ResourceNotFoundException("not found")
        );
        churchUser.setTotalComments(churchUser.getTotalComments() + 1L);
        this.userRepository.save(churchUser);
        video.setTotalComments(video.getTotalComments() + 1L);
        this.videoRepository.save(video);

        VideoComment videoComment = this.videoCommentsRepository.save(
                new VideoComment(
                        commentDto.getComment(),
                        LocalDateTime.now(),
                        user,
                        video
                )
        );

        NotificationDto notificationDto = new NotificationDto(
                user.getUserId(),
                commentDto.getItemId(),
                video.getChurch().getId(),
                "VIDEO",
                "commented on your post"

        );
        this.userService.notification(notificationDto);
        return videoComment;
    }

    @Override
    public String createLikes(Long itemId, String email) {
        AppUser user = this.userRepository.findByEmail(email).orElseThrow(
                () -> new ResourceNotFoundException("user not found")
        );
        Videos video = this.videoRepository.findById(itemId).orElseThrow(
                () -> new ResourceNotFoundException("video not found")
        );
        AppUser churchUser = this.userRepository.findByChurchId(video.getChurch().getId()).orElseThrow(
                () -> new ResourceNotFoundException("not found")
        );
        String status = "unliked";
        Optional<VideoLikes> likedVideo =  this.videoLIkesRepository.getIfLiked(user.getUserId(), itemId);
        if( likedVideo.isPresent() == true){
            videoLIkesRepository.delete(likedVideo.get());
            churchUser.setTotalLikes(churchUser.getTotalLikes() - 1L);
            this.userRepository.save(churchUser);
            video.setTotalLikes(video.getTotalLikes() - 1L);
            this.videoRepository.save(video);
        }
        else {
            churchUser.setTotalLikes(churchUser.getTotalLikes() + 1L);
            this.userRepository.save(churchUser);
            video.setTotalLikes(video.getTotalLikes() + 1L);
            this.videoRepository.save(video);
            videoLIkesRepository.save(
                    new VideoLikes(
                            video, user
                    )
            );
            NotificationDto notificationDto = new NotificationDto(
                    user.getUserId(),
                    itemId,
                    video.getChurch().getId(),
                    "VIDEO",
                    "liked your post"
            );
            this.userService.notification(notificationDto);
            status = "liked";
        }
        return status;
    }

    @Override
    public boolean addToWatch(String email, Long videoId) {
        Videos video = this.videoRepository.findById(videoId).orElseThrow(
                () -> new ResourceNotFoundException("video not found")
        );
        video.setViews(video.getViews() + 1);
        AppUser user = this.userRepository.findByChurchId(video.getChurch().getId()).orElseThrow(
                () -> new ResourceNotFoundException("user not found")
        );
        user.setTotalStreams(user.getTotalStreams() + 1);
        userRepository.save(user);
        this.videoRepository.save(video);
        return true;
    }

    @Override
    public Set<VideoLikes> getLikes(String email) {
        AppUser user = this.userRepository.findByEmail(email).orElseThrow(
                () -> new ResourceNotFoundException("user not found")
        );
        return this.videoLIkesRepository.findByUser(user).orElseThrow(
                () -> new ResourceNotFoundException("no liked audio")
        );
    }

    @Override
    public Set<VideoCommentResponse> getVideoComments(Long videoId) {
      return this.videoRepository.getVideoComment(videoId);
    }

    @Override
    public boolean checkVideoLike(Long videoId, String email) {
        AppUser user = this.userRepository.findByEmail(email).orElseThrow(
                () -> new ResourceNotFoundException("user not found")
        );
        Optional<VideoLikes> video = this.videoLIkesRepository.getIfLiked(user.getUserId(), videoId);
        if (video.isPresent()){
            return true;
        }
        return false;
    }


}
