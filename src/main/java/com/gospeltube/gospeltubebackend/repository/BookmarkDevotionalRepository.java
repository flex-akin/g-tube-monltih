package com.gospeltube.gospeltubebackend.repository;

import com.gospeltube.gospeltubebackend.entity.AppUser;
import com.gospeltube.gospeltubebackend.entity.BookmarkDevotional;
import com.gospeltube.gospeltubebackend.entity.Devotional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.Set;

public interface BookmarkDevotionalRepository extends JpaRepository<BookmarkDevotional, Long> {
    Optional<BookmarkDevotional> findByUserAndDevotional(AppUser user, Devotional devotional);
    Optional<Set<BookmarkDevotional>> findByUser(AppUser user);
    boolean existsByDevotionalAndUser(Devotional devotional, AppUser user);

}
