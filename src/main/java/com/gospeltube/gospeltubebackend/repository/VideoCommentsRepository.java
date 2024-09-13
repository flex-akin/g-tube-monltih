package com.gospeltube.gospeltubebackend.repository;

import com.gospeltube.gospeltubebackend.entity.VideoComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VideoCommentsRepository extends JpaRepository<VideoComment, Long> {
}
