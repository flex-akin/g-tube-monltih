package com.gospeltube.gospeltubebackend.repository;

import com.gospeltube.gospeltubebackend.entity.Church;
import com.gospeltube.gospeltubebackend.entity.Series;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Set;

public interface SeriesRepository extends JpaRepository<Series, Long> {
    Set<Series> findByChurch(Church church);
}
