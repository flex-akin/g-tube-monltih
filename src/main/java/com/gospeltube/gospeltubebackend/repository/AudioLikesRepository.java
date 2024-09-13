package com.gospeltube.gospeltubebackend.repository;

import com.gospeltube.gospeltubebackend.entity.AppUser;
import com.gospeltube.gospeltubebackend.entity.AudioLikes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.Set;

public interface AudioLikesRepository extends JpaRepository<AudioLikes, Long> {
    Optional<Set<AudioLikes>> findByUser(AppUser user);
    @Query(value = """
select *
from audio_likes a
where a.user_id = :userId  and a.audio_id = :audioId
""", nativeQuery = true)
    Optional<AudioLikes> getIfLiked(@Param("userId") Long userId, @Param("audioId") Long audioId);
}
