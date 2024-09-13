package com.gospeltube.gospeltubebackend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name="audio_likes")
public class AudioLikes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "audio_id")
    private Audio audio;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id")
    private AppUser user;

    public AudioLikes( Audio audio, AppUser user) {
        this.user = user;
        this.audio = audio;
    }

    public AudioLikes() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Audio getAudio() {
        return audio;
    }

    public void setAudio(Audio audio) {
        this.audio = audio;
    }

    public AppUser getUser() {
        return user;
    }

    public void setUser(AppUser user) {
        this.user = user;
    }
}
