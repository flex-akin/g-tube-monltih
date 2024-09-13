package com.gospeltube.gospeltubebackend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name="video_likes")
public class VideoLikes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "video_id")
    private Videos video;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id")
    private AppUser user;

    public VideoLikes(Videos video, AppUser user) {
        this.video = video;
        this.user = user;
    }
    public VideoLikes() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Videos getVideo() {
        return video;
    }

    public void setVideo(Videos video) {
        this.video = video;
    }

    public AppUser getUser() {
        return user;
    }

    public void setUser(AppUser user) {
        this.user = user;
    }
}
