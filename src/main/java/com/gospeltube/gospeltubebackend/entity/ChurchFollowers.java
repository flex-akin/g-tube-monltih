package com.gospeltube.gospeltubebackend.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "church_followers")
public class ChurchFollowers {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "church_id")
    private Church church;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private AppUser user;

    public ChurchFollowers(Church church, AppUser user) {
        this.church = church;
        this.user = user;
    }

    public ChurchFollowers() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Church getChurch() {
        return church;
    }

    public void setChurch(Church church) {
        this.church = church;
    }

    public AppUser getUser() {
        return user;
    }

    public void setUser(AppUser user) {
        this.user = user;
    }
}
