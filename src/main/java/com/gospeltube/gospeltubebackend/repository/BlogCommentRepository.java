package com.gospeltube.gospeltubebackend.repository;

import com.gospeltube.gospeltubebackend.entity.BlogComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogCommentRepository extends JpaRepository<BlogComment, Long> {
}
