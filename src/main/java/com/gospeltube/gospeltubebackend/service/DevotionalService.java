package com.gospeltube.gospeltubebackend.service;

import com.gospeltube.gospeltubebackend.dto.DevotionalDto;
import com.gospeltube.gospeltubebackend.dto.DevotionalRes;
import com.gospeltube.gospeltubebackend.entity.BookmarkDevotional;
import com.gospeltube.gospeltubebackend.entity.Devotional;

import java.util.Set;

public interface DevotionalService {
    boolean createDevotional(DevotionalDto devotionalDto, String email);
    DevotionalDto getDevotional(Long devotionalId, String email);
    Set<DevotionalRes> getDevotionals(Long id, String  email);
    Set<DevotionalRes> allDevotionals(String email);
    String bookmark(String email, Long devotionalId);
    Set<BookmarkDevotional> getBookmarked(String email);

}
