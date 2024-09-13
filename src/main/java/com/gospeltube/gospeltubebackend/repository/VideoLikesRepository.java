package com.gospeltube.gospeltubebackend.repository;

import com.gospeltube.gospeltubebackend.entity.AppUser;
import com.gospeltube.gospeltubebackend.entity.VideoLikes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.Set;

public interface VideoLikesRepository extends JpaRepository<VideoLikes, Long> {
    Optional<Set<VideoLikes>> findByUser(AppUser user);

    @Query(value = """
select *
from video_likes a
where a.user_id = :userId  and a.video_id = :videoId
""", nativeQuery = true)
    Optional<VideoLikes> getIfLiked(@Param("userId") Long userId, @Param("videoId") Long videoId);
}
