package com.gospeltube.gospeltubebackend.repository;

import com.gospeltube.gospeltubebackend.entity.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {

}
