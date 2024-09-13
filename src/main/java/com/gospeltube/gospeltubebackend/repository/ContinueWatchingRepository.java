package com.gospeltube.gospeltubebackend.repository;

import com.gospeltube.gospeltubebackend.entity.AppUser;
import com.gospeltube.gospeltubebackend.entity.ContinueWatching;
import com.gospeltube.gospeltubebackend.entity.Videos;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ContinueWatchingRepository extends JpaRepository<ContinueWatching, Long> {
    Optional<ContinueWatching> findByVideoAndUser(Videos video, AppUser user);
}
