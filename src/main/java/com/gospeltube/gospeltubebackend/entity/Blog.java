package com.gospeltube.gospeltubebackend.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name= "blog")
public class Blog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String header;
    @Column(length = 500)
    private String image;
    @Column(length = 1000)
    private String link;
    private LocalDateTime date;
    @Column(columnDefinition="TEXT")
    private String content;
    @Column(name = "total_likes", columnDefinition = "bigint default 0")
    @ColumnDefault("0")
    private Long totalLikes = 0L;
    @ColumnDefault("0")
    @Column(name = "total_comments", columnDefinition = "bigint default 0")
    private Long totalComments = 0L;
    @JsonIgnore
    @OneToMany(mappedBy = "blog", fetch = FetchType.LAZY)
    private Set<BlogComment> comment;
    @ManyToOne
    @JoinColumn(name = "church_id")
    private Church church;

    public Blog(String header, String image, String link, String content, Church church) {
        this.header = header;
        this.image = image;
        this.link = link;
        this.content = content;
        this.church = church;
        this.date = LocalDateTime.now();
    }


    public Church getChurch() {
        return church;
    }

    public void setChurch(Church church) {
        this.church = church;
    }

    public Blog() {
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getTotalLikes() {
        if (totalLikes == null) return 0L;
        return totalLikes;
    }

    public void setTotalLikes(Long totalLikes) {
        this.totalLikes = totalLikes;
    }

    public Long getTotalComments() {
        if (totalComments == null) return 0L;
        return totalComments;
    }

    public void setTotalComments(Long totalComments) {
        this.totalComments = totalComments;
    }

    public Set<BlogComment> getComment() {
        return comment;
    }

    public void setComment(Set<BlogComment> comment) {
        this.comment = comment;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}

