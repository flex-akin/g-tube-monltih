package com.gospeltube.gospeltubebackend.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "blog_likes")
public class BlogLikes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "blog_id")
    private Blog blog;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private AppUser user;

    public BlogLikes(Blog blog, AppUser user) {
        this.blog = blog;
        this.user = user;
    }

    public BlogLikes() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Blog getBlog() {
        return blog;
    }

    public void setBlog(Blog blog) {
        this.blog = blog;
    }

    public AppUser getUser() {
        return user;
    }

    public void setUser(AppUser user) {
        this.user = user;
    }
}
