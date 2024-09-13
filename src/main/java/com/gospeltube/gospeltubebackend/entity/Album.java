package com.gospeltube.gospeltubebackend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name ="album")
public class Album {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(columnDefinition = "TEXT")
    private String title;
    @Column(columnDefinition = "TEXT")
    private String description;
    @Column(length = 1000)
    private String artiste;
    @Column(name = "image_url", length = 1000)
    private String imageUrl;
    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime date;
    @JsonIgnore
    @OneToMany(mappedBy = "album")
    private Set<Audio> audios;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "church_id")
    private Church church;

    public Album(Church church, Set<Audio> audios, LocalDateTime date, String imageUrl, String artiste, String description, String title) {
        this.church = church;
        this.audios = audios;
        this.date = date;
        this.imageUrl = imageUrl;
        this.artiste = artiste;
        this.description = description;
        this.title = title;
    }

    public Album() {
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

    public String getArtiste() {
        return artiste;
    }

    public void setArtiste(String artiste) {
        this.artiste = artiste;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Set<Audio> getAudios() {
        return audios;
    }

    public void setAudios(Set<Audio> audios) {
        this.audios = audios;
    }
}
