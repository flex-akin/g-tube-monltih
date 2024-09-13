package com.gospeltube.gospeltubebackend.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name= "devotionals")
public class Devotional {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String header;
    @Column(columnDefinition="TEXT")
    private String content;
    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime time;
    @Column(name = "bible_ref")
    private String bibleRef;
    @ManyToOne
    @JoinColumn(name = "church_id")
    private Church church;

    public Devotional(String header, String content, LocalDateTime time, Church church, String bibleRef) {
        this.header = header;
        this.content = content;
        this.time = time;
        this.bibleRef = bibleRef;
        this.church = church;
    }

    public Devotional() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public Church getChurch() {
        return church;
    }

    public void setChurch(Church church) {
        this.church = church;
    }

    public String getBibleRef() {
        return bibleRef;
    }

    public void setBibleRef(String bibleRef) {
        this.bibleRef = bibleRef;
    }
}
