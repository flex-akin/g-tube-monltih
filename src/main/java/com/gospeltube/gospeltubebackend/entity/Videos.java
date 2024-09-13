package com.gospeltube.gospeltubebackend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gospeltube.gospeltubebackend.enums.Visibility;
import jakarta.persistence.*;

import java.time.LocalDateTime;


@Entity
@Table(name = "videos")
public class Videos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(columnDefinition = "TEXT")
    private String title;
    @Column(columnDefinition = "TEXT")
    private String description;
    private String callId;
    @Enumerated(EnumType.STRING)
    private Visibility visibility;
    private String tags;
    @Column(columnDefinition = "boolean default false")
    private boolean comment;
    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime time;
    @Column(columnDefinition = "boolean default false")
    private boolean live;
    @Column(columnDefinition = "boolean default false")
    private boolean liveNow;
    @Column(length = 1000)
    private String videoUrl;
    private String thumbnail;
    @Column(name = "total_likes", columnDefinition = "bigint default 0")
    private Long totalLikes = 0L;
    @Column(name = "total_comments", columnDefinition = "bigint default 0")
    private Long totalComments = 0L;
    @Column(name = "views", columnDefinition = "bigint default 0")
    private Long views = 0L;
    @ManyToOne
    @JoinColumn(name = "series_id")
    private Series series;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "church_id")
    private Church church;


    public Videos(String title, String description, String callId, Visibility visibility, String tags, boolean comment, LocalDateTime time, boolean live, boolean liveNow, String videoUrl, String thumbnail, Series series, Church church) {
        this.title = title;
        this.description = description;
        this.callId = callId;
        this.visibility = visibility;
        this.tags = tags;
        this.comment = comment;
        this.time = time;
        this.live = live;
        this.liveNow = liveNow;
        this.videoUrl = videoUrl;
        this.thumbnail = thumbnail;
        this.series = series;
        this.church = church;
    }

    public Videos() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Visibility getVisibility() {
        return visibility;
    }

    public void setVisibility(Visibility visibility) {
        this.visibility = visibility;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public boolean isComment() {
        return comment;
    }

    public void setComment(boolean comment) {
        this.comment = comment;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public Series getSeries() {
        return series;
    }

    public void setSeries(Series series) {
        this.series = series;
    }

    public String getCallId() {
        return callId;
    }

    public void setCallId(String callId) {
        this.callId = callId;
    }

    public boolean isLive() {
        return live;
    }

    public void setLive(boolean live) {
        this.live = live;
    }

    public Church getChurch() {
        return church;
    }

    public void setChurch(Church church) {
        this.church = church;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public boolean isLiveNow() {
        return liveNow;
    }

    public void setLiveNow(boolean liveNow) {
        this.liveNow = liveNow;
    }

    public Long getTotalLikes() {
        return totalLikes;
    }

    public void setTotalLikes(Long totalLikes) {
        this.totalLikes = totalLikes;
    }

    public Long getTotalComments() {
        return totalComments;
    }

    public void setTotalComments(Long totalComments) {
        this.totalComments = totalComments;
    }

    public Long getViews() {
        if (this.views == null) return 0L;
        return views;
    }

    public void setViews(Long views) {
        this.views = views;
    }
}
