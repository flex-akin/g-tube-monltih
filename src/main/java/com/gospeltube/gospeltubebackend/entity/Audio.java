package com.gospeltube.gospeltubebackend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gospeltube.gospeltubebackend.enums.AudioType;
import com.gospeltube.gospeltubebackend.enums.Visibility;
import jakarta.persistence.*;


import java.time.LocalDateTime;

@Entity
@Table(name = "audio")
public class Audio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private AudioType type;
    @Column(name = "audio_url", length = 1000)
    private String audioUrl;
    @Column(name = "image_url", length = 1000)
    private String imageUrl;
    @Column(columnDefinition = "TEXT")
    private String title;
    @Column(length = 1000)
    private String artiste;
    @Column(columnDefinition = "TEXT")
    private String featuring;
    @Enumerated(EnumType.STRING)
    private Visibility visibility;
    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime date;
    private Long listens;
    @Column(columnDefinition = "bigint default 0")
    private Long totalLikes;
    private String duration;
    @ManyToOne
    @JoinColumn(name = "album_id")
    private Album album;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "church_id")
    private Church church;

    public Audio(AudioType type, String audioUrl, String imageUrl, String title, String artiste, String featuring, Visibility visibility, LocalDateTime date, Album album, Church church, String duration) {
        this.type = type;
        this.audioUrl = audioUrl;
        this.imageUrl = imageUrl;
        this.title = title;
        this.artiste = artiste;
        this.featuring = featuring;
        this.visibility = visibility;
        this.date = date;
        this.album = album;
        this.church = church;
        this.duration = duration;
    }

    public Audio() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AudioType getType() {
        return type;
    }

    public void setType(AudioType type) {
        this.type = type;
    }

    public String getAudioUrl() {
        return audioUrl;
    }

    public void setAudioUrl(String audioUrl) {
        this.audioUrl = audioUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtiste() {
        return artiste;
    }

    public void setArtiste(String artiste) {
        this.artiste = artiste;
    }

    public String getFeaturing() {
        return featuring;
    }

    public void setFeaturing(String featuring) {
        this.featuring = featuring;
    }

    public Visibility getVisibility() {
        return visibility;
    }

    public void setVisibility(Visibility visibility) {
        this.visibility = visibility;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Album getAlbum() {
        return album;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }

    public Long getListens() {
        if (this.listens == null) return 0L;
        return listens;
    }

    public void setListens(Long listens) {
        this.listens = listens;
    }

    public Church getChurch() {
        return church;
    }

    public void setChurch(Church church) {
        this.church = church;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public Long getTotalLikes() {
        return totalLikes;
    }

    public void setTotalLikes(Long totalLikes) {
        this.totalLikes = totalLikes;
    }
}
