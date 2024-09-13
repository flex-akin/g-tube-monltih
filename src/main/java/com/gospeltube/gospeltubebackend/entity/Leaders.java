package com.gospeltube.gospeltubebackend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name="church_leaders")
public class Leaders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String image;
    private String name;
    private String bio;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "church_id")
    private Church church;

    public Leaders(String image, String name, String bio, Church church) {

        this.image = image;
        this.name = name;
        this.bio = bio;
        this.church = church;
    }

    public Leaders() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public Church getChurch() {
        return church;
    }

    public void setChurch(Church church) {
        this.church = church;
    }
}
