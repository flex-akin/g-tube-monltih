package com.gospeltube.gospeltubebackend.repository;

import com.gospeltube.gospeltubebackend.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
}
