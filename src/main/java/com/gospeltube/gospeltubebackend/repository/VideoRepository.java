package com.gospeltube.gospeltubebackend.repository;

import com.gospeltube.gospeltubebackend.dto.VideoCommentResponse;
import com.gospeltube.gospeltubebackend.dto.VideoResponse;
import com.gospeltube.gospeltubebackend.dto.VideoResponseExt;
import com.gospeltube.gospeltubebackend.entity.Videos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Set;

public interface VideoRepository extends JpaRepository<Videos, Long> {
    Set<Videos> findAllByLive(boolean isLive);
    @Query(value= """
    SELECT
    a.id AS id,
    a.title,
    a.description,
    a.call_id AS callId,
    a.visibility,
    a.tags,
    a.comment,
    a.video_url AS videoUrl,
    a.thumbnail,
    a.live,
    a.live_now AS liveNow,
    a.series_id AS seriesId,
    b.church_name AS churchName,
    b.logo,
    c.user_id AS churchId,
    c.stream_id AS streamId,
    c.stream_id_ext AS streamIdExt,
    d.title AS seriesName,
    a.time AS date,
    a.views AS views,
    CASE
        WHEN a.id = f.video_id AND f.user_id = :userId THEN true
        ELSE false
    END AS liked
FROM
    videos a
LEFT JOIN
    church b ON a.church_id = b.id
LEFT JOIN
    users c ON a.church_id = c.church_id
LEFT JOIN
    series d ON a.series_id = d.id
LEFT JOIN
    video_likes f ON a.id = f.video_id AND f.user_id = :userId
WHERE
    a.live_now = :live
""", nativeQuery = true)
    Set<VideoResponse> getVideosByLive(@Param("live") boolean live, @Param("userId") Long userId);
    @Query(value= """
select a.id as id, a.title, a.description, a.call_id as callId, a.visibility, a.tags, a.comment, a.video_url as videoUrl, a.thumbnail, a.live,
a.live_now as liveNow, a.series_id as seriesId, b.church_name as churchName , c.user_id as churchId, b.logo, c.stream_id as streamId, c.stream_id_ext as streamIdExt,
d.title as seriesName, a.time as date, a.views as views ,  case when a.id = f.video_id and f.user_id = :userId then truefrom videos a
else false end as liked
from videos a
left join church b on  a.church_id = b.id
left join users c on a.church_id = b.id and c.church_id = b.id
left join series d on  a.series_id = d.id
left join video_likes f on a.id = f.video_id and f.user_id = :userId
""", nativeQuery = true)
    Set<VideoResponse> getAllVideos(@Param("userId") Long userId);
    @Query(value= """

    SELECT
    a.id AS id,
    a.title,
    a.description,
    a.call_id AS callId,
    a.visibility,
    a.tags,
    a.comment,
    a.video_url AS videoUrl,
    a.thumbnail,
    a.live,
    a.live_now AS liveNow,
    a.series_id AS seriesId,
    b.church_name AS churchName,
    b.logo,
    c.user_id AS churchId,
    c.stream_id AS streamId,
    c.stream_id_ext AS streamIdExt,
    d.title AS seriesName,
    a.time AS date,
    a.views AS views,
    CASE
        WHEN a.id = f.video_id AND f.user_id = :userId THEN true
        ELSE false
    END AS liked
FROM
    videos a
LEFT JOIN
    church b ON a.church_id = b.id
LEFT JOIN
    users c ON a.church_id = c.church_id
LEFT JOIN
    series d ON a.series_id = d.id
LEFT JOIN
    video_likes f ON a.id = f.video_id AND f.user_id = :userId
WHERE
    a.live_now = :live AND b.id = :churchId

""", nativeQuery = true)
    Set<VideoResponse> getVideosByChurchLive(@Param("live") boolean live, @Param("churchId") Long churchId, @Param("userId") Long userId);

    @Query(value = """
select a.id as commentId, a.comment, a.time as date, a.total_likes as likes, b.profile_picture as proefilePicture,
b.first_name as firstName, b.last_name as lastName, b.user_id as userId from video_comment a, users b where a.video_id = :videoId and
b.user_id = a.user_id
""", nativeQuery = true
    )
    Set<VideoCommentResponse> getVideoComment(@Param("videoId") Long videoId);

    @Query(value= """
select a.id as id, a.title, a.description, a.call_id as callId, a.visibility, a.tags, a.comment, a.video_url as videoUrl, a.thumbnail, a.live,
       a.live_now as liveNow, a.series_id as seriesId, b.church_name as churchName, b.logo , c.user_id as churchId, c.stream_id as streamId, c.stream_id_ext as streamIdExt,
       d.title as seriesName, a.time as date, e.id as watchId, e.time as currentTime, a.views as views, case when a.id = f.video_id and f.user_id = :userId then true
                                                                                                             else false end as liked
from videos a
         left join continue_watching e on a.id = e.video_id and user_id = :userId
         left join church b on  a.church_id = b.id
         left join users c on a.church_id = b.id and c.church_id = b.id
         left join series d on  a.series_id = d.id
         left join video_likes f on a.id = f.video_id and f.user_id = :userId
where e.user_id = :userId
""", nativeQuery = true)
    Set<VideoResponseExt> getContinueWatching(@Param("userId") Long userId);
}
