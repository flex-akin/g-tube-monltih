package com.gospeltube.gospeltubebackend.repository;

import com.gospeltube.gospeltubebackend.entity.Album;
import com.gospeltube.gospeltubebackend.entity.Church;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.Set;

public interface AlbumRepository extends JpaRepository<Album, Long> {
    Optional<Set<Album>> findByChurch(Church church);
}
