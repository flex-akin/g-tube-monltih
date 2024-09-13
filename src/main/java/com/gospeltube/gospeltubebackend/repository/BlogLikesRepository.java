package com.gospeltube.gospeltubebackend.repository;

import com.gospeltube.gospeltubebackend.entity.AppUser;
import com.gospeltube.gospeltubebackend.entity.AudioLikes;
import com.gospeltube.gospeltubebackend.entity.Blog;
import com.gospeltube.gospeltubebackend.entity.BlogLikes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface BlogLikesRepository extends JpaRepository<BlogLikes, Long> {
    Optional<BlogLikes> findByUserAndBlog(AppUser user, Blog blog );
    boolean existsByUserAndBlog(AppUser user, Blog blog);

    @Query(value = """
        select *
        from blog_likes a
        where a.user_id = :userId  and a.blog_id = :blogId
""", nativeQuery = true)
    Optional<BlogLikes> getIfLiked(@Param("userId") Long userId, @Param("blogId") Long blogId);
}
