package com.gospeltube.gospeltubebackend.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "comment_likes")
public class CommentLikes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "comment_id")
    private Long commentId;
    @Column(name = "user_id")
    private Long userId;
    @Column(name = "content_type")
    private String contentType;

    public CommentLikes() {
    }

    public CommentLikes(Long commentId, Long userId, String contentType) {
        this.commentId = commentId;
        this.userId = userId;
        this.contentType = contentType;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Long getCommentId() {
        return commentId;
    }

    public void setCommentId(Long commentId) {
        this.commentId = commentId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }
}
