package com.gospeltube.gospeltubebackend.controller;

import com.gospeltube.gospeltubebackend.dto.*;
import com.gospeltube.gospeltubebackend.entity.Blog;
import com.gospeltube.gospeltubebackend.exception.ResourceNotFoundException;
import com.gospeltube.gospeltubebackend.service.BlogService;
import com.gospeltube.gospeltubebackend.util.Utilities;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@RestController
@Validated
@CrossOrigin("*")
@RequestMapping("api/v1/blog")
public class BlogController {

    private final BlogService blogService;
    private final JwtDecoder jwtDecoder;

    public BlogController(BlogService blogService, JwtDecoder jwtDecoder) {
        this.blogService = blogService;
        this.jwtDecoder = jwtDecoder;
    }

    @PostMapping
    public ResponseEntity<Response> createBlog (@Valid @RequestBody BlogDto blogDto,
                                                @RequestHeader(name = "Authorization") String authorizationHeader  ){
        Utilities utilities = new Utilities(jwtDecoder);
        String email = utilities.getPrincipal(authorizationHeader);
        this.blogService.createBlog(blogDto, email);
        return new ResponseEntity<>(new Response("00", "Blog posted successfully",true, null), HttpStatus.CREATED);
    }

    @PostMapping("/like")
    public ResponseEntity<Response> likeBlog (
            @RequestParam("blogId") Optional<Long> blogId,
            @RequestHeader(name = "Authorization") String authorizationHeader
    ){
        Utilities utilities = new Utilities(jwtDecoder);
        String email = utilities.getPrincipal(authorizationHeader);
         Long blog;
         blog = blogId.orElseThrow(
                 () -> new ResourceNotFoundException("blog not defined")
         );
        String status = this.blogService.likeBlog(email, blog);
        return new ResponseEntity<>(new Response("00", status,true, null), HttpStatus.OK);
    }

    @PostMapping("/comment")
    public ResponseEntity<Response> comment (
            @Valid @RequestBody CommentDto commentDto,
            @RequestHeader(name = "Authorization") String authorizationHeader
    ){
        Utilities utilities = new Utilities(jwtDecoder);
        String email = utilities.getPrincipal(authorizationHeader);
        this.blogService.comment(commentDto.getComment(), commentDto.getItemId(), email);
        return new ResponseEntity<>(new Response("00", "successful",true, null), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Response> getBlogs (
            @RequestParam("churchId") Optional<Long> churchId,
            @RequestHeader(name = "Authorization") String authorizationHeader
    ){
        Utilities utilities = new Utilities(jwtDecoder);
        String email = utilities.getPrincipal(authorizationHeader);
        Set<BlogResDto> value;
        if (churchId.isPresent()){
            value = this.blogService.getBlogsByChurch(churchId.get(), email);
        }
        else{
            value= this.blogService.getAllBlogs(email);
        }
        return new ResponseEntity<>(new Response("00", "blogs fetched successfully",true, value), HttpStatus.OK);

    }

    @GetMapping("/{blogId}")
    public ResponseEntity<Response> getBlog(
            @PathVariable String blogId,
            @RequestHeader(name = "Authorization") String authorizationHeader
    ){
        Utilities utilities = new Utilities(jwtDecoder);
        String email = utilities.getPrincipal(authorizationHeader);
        Long id = Long.parseLong(blogId);
        BlogResDto blog = this.blogService.getBlog(id, email);
        Set<BlogResDto> setBlog = new HashSet<>();
        setBlog.add(blog);
        return new ResponseEntity<>(new Response("00", "blog fetched", true, setBlog ), HttpStatus.OK );
    }

    @GetMapping("/blogComment/{blogId}")
    public ResponseEntity<Response> getBlogComment(
            @PathVariable String blogId
    ){

        Long id = Long.parseLong(blogId);
        Set<BlogResponse> blogComments = this.blogService.blogComments(id);
        return new ResponseEntity<>(new Response("00", "blogs comment fetched successfully",true, blogComments), HttpStatus.OK);
    }
}
