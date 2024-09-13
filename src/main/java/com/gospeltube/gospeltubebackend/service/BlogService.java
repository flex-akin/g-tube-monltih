package com.gospeltube.gospeltubebackend.service;

import com.gospeltube.gospeltubebackend.dto.BlogDto;
import com.gospeltube.gospeltubebackend.dto.BlogResDto;
import com.gospeltube.gospeltubebackend.dto.BlogResponse;
import com.gospeltube.gospeltubebackend.entity.Blog;


import java.util.Set;

public interface BlogService {
    Blog createBlog(BlogDto blogDto, String email);
    String likeBlog(String email, Long blogId);
    boolean comment(String comment, Long blogId, String email);
    Set<BlogResDto> getAllBlogs(String email);
    Set<BlogResDto> getBlogsByChurch(Long churchId, String email);
    BlogResDto getBlog(Long blogId, String email);
    Set<BlogResponse> blogComments(Long blogId);
}
