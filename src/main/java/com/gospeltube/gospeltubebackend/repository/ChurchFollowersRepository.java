package com.gospeltube.gospeltubebackend.repository;

import com.gospeltube.gospeltubebackend.dto.UserFollowed;
import com.gospeltube.gospeltubebackend.entity.AppUser;
import com.gospeltube.gospeltubebackend.entity.Church;
import com.gospeltube.gospeltubebackend.entity.ChurchFollowers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.Set;

public interface ChurchFollowersRepository extends JpaRepository<ChurchFollowers, Long> {
    @Query(value = """
select *
from church_followers a
where a.user_id = :userId  and a.church_id = :churchId
""", nativeQuery = true)
    Optional<ChurchFollowers> getIfFollow(@Param("userId") Long userId, @Param("churchId") Long churchId);

    @Query(value = """
        select a.first_name as churchName, a.user_id as churchId,  b.description as description ,b.logo as profilePicture,
        a.total_posts as totoalPosts, a.total_subs as totatSubscribers, a.total_likes as totalLikes,
        a.total_streams as totalStreams, true as following
        from users a, church b, church_followers c where a.church_id = b.id and b.id = c.church_id and
        c.user_id = :userId
        """, nativeQuery = true
    )
    Optional<Set<UserFollowed>> getFollowedChurches(@Param("userId") Long userId);
    boolean existsByChurchAndUser(Church church, AppUser user);

}