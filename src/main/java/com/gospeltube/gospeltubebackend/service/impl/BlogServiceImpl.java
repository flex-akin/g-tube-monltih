package com.gospeltube.gospeltubebackend.service.impl;

import com.gospeltube.gospeltubebackend.dto.BlogDto;
import com.gospeltube.gospeltubebackend.dto.BlogResDto;
import com.gospeltube.gospeltubebackend.dto.BlogResponse;
import com.gospeltube.gospeltubebackend.dto.NotificationDto;
import com.gospeltube.gospeltubebackend.entity.*;
import com.gospeltube.gospeltubebackend.exception.ResourceNotFoundException;
import com.gospeltube.gospeltubebackend.repository.*;
import com.gospeltube.gospeltubebackend.service.BlogService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
public class BlogServiceImpl implements BlogService {

    private final BlogRepository blogRepository;
    private final UserRepository userRepository;
    private final BlogLikesRepository blogLikesRepository;
    private final BlogCommentRepository blogCommentRepository;
    private final ChurchRepository churchRepository;
    private final UserService userService;

    public BlogServiceImpl(BlogRepository blogRepository, UserRepository userRepository, BlogLikesRepository blogLikesRepository, BlogCommentRepository blogCommentRepository, ChurchRepository churchRepository, UserService userService) {
        this.blogRepository = blogRepository;
        this.userRepository = userRepository;
        this.blogLikesRepository = blogLikesRepository;
        this.blogCommentRepository = blogCommentRepository;
        this.churchRepository = churchRepository;
        this.userService = userService;
    }

    @Override
    public Blog createBlog(BlogDto blogDto, String email) {
        AppUser user = this.userRepository.findByEmail(email).orElseThrow(
                () -> new ResourceNotFoundException("user not found")
        );
        Church church = this.churchRepository.findById(user.getChurch().getId()).orElseThrow(
                () -> new ResourceNotFoundException("you can't create a blog")
        );
        user.setTotalPosts(user.getTotalPosts() + 1L);
        this.userRepository.save(user);
        Blog blog = new Blog(
                blogDto.getHeader(),
                blogDto.getImage(),
                blogDto.getLink(),
                blogDto.getContent(),
                church
        );
        return this.blogRepository.save(blog);
    }

    @Override
    public String likeBlog(String email, Long blogId) {
        AppUser user = this.userRepository.findByEmail(email).orElseThrow(
                () -> new ResourceNotFoundException("user not found")
        );
        Blog blog = this.blogRepository.findById(blogId).orElseThrow(
                () -> new ResourceNotFoundException("blog not found")
        );
        AppUser churchUser = this.userRepository.findByChurchId(blog.getChurch().getId()).orElseThrow(
                () -> new ResourceNotFoundException("not found")
        );
        String status = "unliked";
        Optional<BlogLikes> likedBlog =  this.blogLikesRepository.getIfLiked(user.getUserId(), blogId);
        if(likedBlog.isPresent()) {
            this.blogLikesRepository.save(
                    new BlogLikes(
                            blog, user
                    )
            );
            Long newTotal = blog.getTotalLikes() + 1;
            blog.setTotalLikes(newTotal);
            this.blogRepository.save(blog);
        }
        else {
            churchUser.setTotalLikes(churchUser.getTotalLikes() + 1L);
            this.userRepository.save(churchUser);
            blog.setTotalLikes(blog.getTotalLikes() + 1L);
            this.blogRepository.save(blog);
            blogLikesRepository.save(
                    new BlogLikes(
                            blog, user
                    )
            );
            NotificationDto notificationDto = new NotificationDto(
                    user.getUserId(),
                    blogId,
                    blog.getChurch().getId(),
                    "BLOG",
                    "liked your post"
            );
            this.userService.notification(notificationDto);
            status = "liked";
        }

        return status;
    }

    @Override
    public boolean comment(String comment, Long blogId, String email) {
        AppUser user = this.userRepository.findByEmail(email).orElseThrow(
                () -> new ResourceNotFoundException("user not found")
        );
        Blog blog = this.blogRepository.findById(blogId).orElseThrow(
                () -> new ResourceNotFoundException("blog not found")
        );
        this.blogCommentRepository.save(
                new BlogComment(
                        comment,
                        LocalDateTime.now(),
                        user,
                        blog
                )
        );
        Long newTotal = blog.getTotalComments() + 1;
        blog.setTotalComments(newTotal);
        this.blogRepository.save(blog);
        return true;
    }

    @Override
    public Set<BlogResDto> getBlogsByChurch (Long churchId, String email){
        AppUser churchUser = this.userRepository.findById(churchId).orElseThrow(
                () -> new ResourceNotFoundException("user not found")
        );
        AppUser user = this.userRepository.findByEmail(email).orElseThrow(
                () -> new ResourceNotFoundException("user not found")
        );
        return this.blogRepository.findByChurch(churchUser.getChurch().getId(), user.getUserId());
    }

    public Set<BlogResDto> getAllBlogs ( String email){

        AppUser user = this.userRepository.findByEmail(email).orElseThrow(
                () -> new ResourceNotFoundException("user not found")
        );
        return this.blogRepository.findAllBlogs(user.getUserId());
    }

    @Override
    public BlogResDto getBlog (Long blogId, String email){
        AppUser user = this.userRepository.findByEmail(email).orElseThrow(
                () -> new ResourceNotFoundException("user not found")
        );
        return this.blogRepository.findSingleBlog(blogId, user.getUserId());
    }

    @Override
    public Set<BlogResponse> blogComments(Long blogId) {
        return this.blogRepository.getBlogComment(blogId);
    }
}
