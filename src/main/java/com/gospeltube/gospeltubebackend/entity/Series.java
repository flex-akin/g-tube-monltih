package com.gospeltube.gospeltubebackend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gospeltube.gospeltubebackend.enums.Visibility;
import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "series")
public class Series {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;
    @Column(columnDefinition = "TEXT")
    private String title;
    @Column(columnDefinition = "TEXT")
    private String description;
    private String tags;
    private String language;
    @Enumerated(EnumType.STRING)
    private Visibility visibility;
    @JsonIgnore
    @OneToMany(mappedBy = "series")
    private Set<Videos> videos;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "church_id")
    private Church church;

    public Series( String title, String description, String tags, String language, Visibility visibility, Church church) {
        this.title = title;
        this.description = description;
        this.tags = tags;
        this.language = language;
        this.visibility = visibility;
        this.church = church;
    }

    public Series() {
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

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        language = language;
    }

    public Visibility getVisibility() {
        return visibility;
    }

    public void setVisibility(Visibility visibility) {
        this.visibility = visibility;
    }

    public Set<Videos> getVideos() {
        return videos;
    }

    public void setVideos(Set<Videos> videos) {
        this.videos = videos;
    }
    public Church getChurch() {
        return church;
    }

    public void setChurch(Church church) {
        this.church = church;
    }

}
