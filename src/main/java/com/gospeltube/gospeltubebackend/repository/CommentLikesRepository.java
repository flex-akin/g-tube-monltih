package com.gospeltube.gospeltubebackend.repository;

import com.gospeltube.gospeltubebackend.entity.CommentLikes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentLikesRepository extends JpaRepository<CommentLikes, Long> {
}
