package com.gospeltube.gospeltubebackend.repository;

import com.gospeltube.gospeltubebackend.dto.DevotionalRes;
import com.gospeltube.gospeltubebackend.entity.Church;
import com.gospeltube.gospeltubebackend.entity.Devotional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.Set;

public interface DevotionalRepository extends JpaRepository<Devotional, Long> {
    Optional<Set<Devotional>> findByChurch(Church church);
    @Query(
           value = """
        select a.* from devotionals a, bookmark_devotional b where b.user_id = :id
        and a.id = b.devotional_id
        """, nativeQuery = true
    )
    Optional<Set<Devotional>> findByBookmarked(@Param("id") Long id);
    @Query(
            value = """
        select a.*, a.bible_ref as bibleRef, a.church_id as churchId ,case when a.id = b.devotional_id and b.user_id = :userId then true
        else false end as bookmarked from devotionals a
        left join bookmark_devotional b on a.id = b.devotional_id and b.user_id = :userId
        """, nativeQuery = true
    )
    Optional<Set<DevotionalRes>> findAllDevotional(@Param("userId") Long userId);
    @Query(
            value = """
        select a.*, a.bible_ref as bibleRef,  a.church_id as churchId , case when a.id = b.devotional_id and b.user_id = :userId then true
        else false end as bookmarked from devotionals a
        left join bookmark_devotional b on a.id = b.devotional_id and b.user_id = :userId
        where a.church_id = :churchId
        """, nativeQuery = true
    )
    Optional<Set<DevotionalRes>> findAllDevotionalByChurch(@Param("churchId") Long churchId, @Param("userId") Long userId);
}

