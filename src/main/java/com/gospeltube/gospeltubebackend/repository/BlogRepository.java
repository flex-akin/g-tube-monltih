package com.gospeltube.gospeltubebackend.repository;

import com.gospeltube.gospeltubebackend.dto.BlogResDto;
import com.gospeltube.gospeltubebackend.dto.BlogResponse;
import com.gospeltube.gospeltubebackend.entity.Blog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.Set;

public interface BlogRepository extends JpaRepository<Blog, Long> {

    @Query(
            value = """
        select a.id, a.content, a.image, a.header, a.link, a.total_comments as totalComments, 
        a.total_likes as totalLikes, a.church_id as churchId, c.logo, d.profile_picture as profilePicture, d.first_name as churchName, a.date, case when a.id = b.blog_id and b.user_id = :userId then true
        else false end as liked from blog a
        left join blog_likes b on a.id = b.blog_id and b.user_id = :userId
        left join church c on a.church_id = c.id
        left join users d on a.church_id = c.id and d.church_id = c.id
        where a.church_id = :churchId 
        """, nativeQuery = true
    )
    Set<BlogResDto> findByChurch(Long churchId, Long userId);

    @Query(
            value = """
        select a.id, a.content, a.image, a.header, a.link, a.total_comments as totalComments, 
        a.total_likes as totalLikes, a.church_id as churchId ,  c.logo, d.profile_picture as profilePicture, d.first_name as churchName,
        a.date, case when a.id = b.blog_id and b.user_id = :userId then true
        else false end as liked from blog a
        left join blog_likes b on a.id = b.blog_id and b.user_id = :userId
        left join church c on a.church_id = c.id
        left join users d on a.church_id = c.id and d.church_id = c.id
        where a.id = :blogId 
        """, nativeQuery = true
    )
    BlogResDto findSingleBlog(Long blogId, Long userId);
    @Query(
            value = """
        select a.id, a.content, a.image, a.header, a.link, a.total_comments as totalComments, 
        a.total_likes as totalLikes, a.church_id as churchId ,  c.logo, d.profile_picture as profilePicture, d.first_name as churchName, a.date , case when a.id = b.blog_id and b.user_id = :userId then true
        else false end as liked from blog a
            left join blog_likes b on a.id = b.blog_id and b.user_id = :userId
             left join church c on a.church_id = c.id
        left join users d on a.church_id = c.id and d.church_id = c.id
        
        """, nativeQuery = true
    )
    Set<BlogResDto> findAllBlogs(Long userId);
    @Query(
            value = """
        select a.* from blog a, blog_likes b where b.user_id = :id
        and a.id = b.blog_id
        """, nativeQuery = true
    )
    Optional<Set<Blog>> findLiked(@Param("id") Long id);

    @Query(
            value = """
select a.comment , a.total_likes as likes, a.time, b.profile_picture as profilePicture, b.first_name as firstName,
       b.last_name as lastName, b.user_id as userId from blog_comments a, users b where a.user_id = b.user_id 
and a.id = :blogId
""", nativeQuery = true
    )
    Set<BlogResponse> getBlogComment(@Param("blogId") Long blogId);
}
