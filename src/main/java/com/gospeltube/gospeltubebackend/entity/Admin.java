package com.gospeltube.gospeltubebackend.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "admin_totals")
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false, columnDefinition = "bigint default 0")
    private Long churches = 0L;
    @Column(nullable = false, columnDefinition = "bigint default 0")
    private Long users = 0L;
    @Column(nullable = false, columnDefinition = "bigint default 0")
    private Long disabled = 0L;
    @Column(nullable = false, columnDefinition = "bigint default 0")
    private Long videos = 0L;
    @Column(nullable = false, columnDefinition = "bigint default 0")
    private Long audios = 0L;
    @Column(nullable = false, columnDefinition = "bigint default 0")
    private Long blogs = 0L;
    @Column(nullable = false, columnDefinition = "bigint default 0")
    private Long male = 0L;
    @Column(nullable = false, columnDefinition = "bigint default 0")
    private Long female = 0L;
    @Column(nullable = false)
    private BigDecimal amount = new BigDecimal(0);
    public Admin(){}

    public Admin(Long female, Long male, Long blogs, Long audios, Long videos, Long disabled, Long users, Long churches) {
        this.female = female;
        this.male = male;
        this.blogs = blogs;
        this.audios = audios;
        this.videos = videos;
        this.disabled = disabled;
        this.users = users;
        this.churches = churches;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getChurches() {
        return churches;
    }

    public void setChurches(Long churches) {
        this.churches = churches;
    }

    public Long getUsers() {
        return users;
    }

    public void setUsers(Long users) {
        this.users = users;
    }

    public Long getDisabled() {
        return disabled;
    }

    public void setDisabled(Long disabled) {
        this.disabled = disabled;
    }

    public Long getVideos() {
        return videos;
    }

    public void setVideos(Long videos) {
        this.videos = videos;
    }

    public Long getAudios() {
        return audios;
    }

    public void setAudios(Long audios) {
        this.audios = audios;
    }

    public Long getBlogs() {
        return blogs;
    }

    public void setBlogs(Long blogs) {
        this.blogs = blogs;
    }

    public Long getMale() {
        return male;
    }

    public void setMale(Long male) {
        this.male = male;
    }

    public Long getFemale() {
        return female;
    }

    public void setFemale(Long female) {
        this.female = female;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
