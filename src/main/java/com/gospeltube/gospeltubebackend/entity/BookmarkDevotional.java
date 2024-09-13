package com.gospeltube.gospeltubebackend.entity;

import jakarta.persistence.*;

@Entity
@Table(name ="bookmark_devotional")
public class BookmarkDevotional {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name ="devotional_id")
    private Devotional devotional;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private AppUser user;

    public BookmarkDevotional(Devotional devotional, AppUser user) {
        this.devotional = devotional;
        this.user = user;
    }

    public BookmarkDevotional() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Devotional getDevotional() {
        return devotional;
    }

    public void setDevotional(Devotional devotional) {
        this.devotional = devotional;
    }

    public AppUser getUser() {
        return user;
    }

    public void setUser(AppUser user) {
        this.user = user;
    }
}
