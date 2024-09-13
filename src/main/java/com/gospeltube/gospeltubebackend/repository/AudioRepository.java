package com.gospeltube.gospeltubebackend.repository;

import com.gospeltube.gospeltubebackend.entity.Audio;
import com.gospeltube.gospeltubebackend.entity.Church;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.Set;

public interface AudioRepository extends JpaRepository<Audio, Long> {
    Optional <Set<Audio>> findByChurch(Church church);
}
