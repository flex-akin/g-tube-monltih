package com.gospeltube.gospeltubebackend.controller;

import com.gospeltube.gospeltubebackend.dto.*;
import com.gospeltube.gospeltubebackend.entity.Blog;
import com.gospeltube.gospeltubebackend.entity.Devotional;
import com.gospeltube.gospeltubebackend.exception.BadRequestException;
import com.gospeltube.gospeltubebackend.service.impl.UserService;
import com.gospeltube.gospeltubebackend.util.Utilities;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    private final UserService userService;
    private final JwtDecoder jwtDecoder;

    public UserController(UserService userService,  JwtDecoder jwtDecoder) {
        this.userService = userService;
        this.jwtDecoder = jwtDecoder;
    }


    @GetMapping("/getChurches")
   public ResponseEntity<Response> getChurches(
            @RequestHeader(name = "Authorization") String authorizationHeader
    ){
        Utilities utilities = new Utilities(jwtDecoder);
        String email = utilities.getPrincipal(authorizationHeader);
        Set<ChurchResponseDto> appUser = this.userService.getChurches(email);
        return new ResponseEntity<>(new Response(
                "00",
                "data fetched successfully",
                true,
                appUser
        ), HttpStatus.OK);
    }

    @PostMapping("/follow/{churchId}")
    public ResponseEntity<Response> followChurch(
            @PathVariable String churchId,
            @RequestHeader(name = "Authorization") String authorizationHeader
    ){
        Utilities utilities = new Utilities(jwtDecoder);
        String email = utilities.getPrincipal(authorizationHeader);
        Long church_id = Long.parseLong(churchId);
        boolean follow = this.userService.followChurch(email, church_id);
        String message;
        if(follow){
            message = "followed";
        }
        else{
            message = "unfollowed";
        }
        return new ResponseEntity<>(new Response(
                "00",
                message,
                true,
                null
        ), HttpStatus.OK);
    }

    @GetMapping("/churchSearch")
    public ResponseEntity<Response> searchChurch(
             @RequestParam("name") Optional<String> name
    ){
        String churchName = name.orElseThrow(
                () -> new BadRequestException("no search parameter")
        );
        Set<ChurchResponseDto> appUser = this.userService.searchChurch(churchName);
        return new ResponseEntity<>(new Response(
                "00",
                "data fetched successfully",
                true,
                appUser
        ), HttpStatus.OK);
    }

    @GetMapping("/BookmarkedDevotionals")
    public ResponseEntity<Response> getBookmarkedDevotionals(
            @RequestHeader(name = "Authorization") String authorizationHeader
    ){
        Utilities utilities = new Utilities(jwtDecoder);
        String email = utilities.getPrincipal(authorizationHeader);
        Set<Devotional> devotionals = this.userService.getBookmarkedDevotionals(email);
        return new ResponseEntity<>(new Response(
                "00",
                "data fetched successfully",
                true,
                devotionals
        ), HttpStatus.OK);
    }

    @GetMapping("/LikedBlogs")
    public ResponseEntity<Response> getLikedDevotionals(
            @RequestHeader(name = "Authorization") String authorizationHeader
    ){
        Utilities utilities = new Utilities(jwtDecoder);
        String email = utilities.getPrincipal(authorizationHeader);
        Set<Blog> blogs = this.userService.getLiked(email);
        return new ResponseEntity<>(new Response(
                "00",
                "data fetched successfully",
                true,
                blogs
        ), HttpStatus.OK);
    }

    @GetMapping("/getChurchByUser/{churchId}")
    public ResponseEntity<Response> userGetSingleChurch(
            @PathVariable String churchId,
            @RequestHeader(name = "Authorization") String authorizationHeader
    ){
        Utilities utilities = new Utilities(jwtDecoder);
        String email = utilities.getPrincipal(authorizationHeader);
        Long church_id = Long.parseLong(churchId);
//        boolean follow = this.userService.followChurch(email, church_id);
        UserChurchDto userChurchDto = this.userService.userGetSingleChurch(email, church_id);
        Set<UserChurchDto> userChurchDtoSet = new HashSet<>();
        userChurchDtoSet.add(userChurchDto);
        return new ResponseEntity<>(new Response(
                "00",
                "data fetched successfully",
                true,
                userChurchDtoSet
        ), HttpStatus.OK);
    }

    @PostMapping("/updateChurch")
    public ResponseEntity<Response> updateChurch(
            @RequestBody UpdateChurchDto updateChurchDto,
            @RequestHeader(name = "Authorization") String authorizationHeader
            ){
        Utilities utilities = new Utilities(jwtDecoder);
        String email = utilities.getPrincipal(authorizationHeader);
        this.userService.updateChurch(updateChurchDto, email);
        return new ResponseEntity<>(new Response(
                "00",
                "updated",
                true,
                null
        ), HttpStatus.OK);
    }

    @PostMapping("/updateUser")
    public ResponseEntity<Response> updateUser(
            @RequestBody UpdateUserDto updateUserDto,
            @RequestHeader(name = "Authorization") String authorizationHeader
    ){
        Utilities utilities = new Utilities(jwtDecoder);
        String email = utilities.getPrincipal(authorizationHeader);
        this.userService.updateUser(updateUserDto, email);
        return new ResponseEntity<>(new Response(
                "00",
                "updated",
                true,
                null
        ), HttpStatus.OK);
    }

    @PostMapping("/deleteAccount")
    public ResponseEntity<Response> deleteAccount(
            @RequestHeader(name = "Authorization") String authorizationHeader
    ){
        Utilities utilities = new Utilities(jwtDecoder);
        String email = utilities.getPrincipal(authorizationHeader);
        this.userService.deleteAccount(email);
        return new ResponseEntity<>(new Response(
                "00",
                "account deleted",
                true,
                null
        ), HttpStatus.OK);
    }

    @PostMapping("/changePassword")
    public ResponseEntity<Response> changePassword(
            @RequestBody PasswordDto passwordDto,
            @RequestHeader(name = "Authorization") String authorizationHeader
    ){
        Utilities utilities = new Utilities(jwtDecoder);
        String email = utilities.getPrincipal(authorizationHeader);
        this.userService.changePassword(email, passwordDto.getPassword());
        return new ResponseEntity<>(new Response(
                "00",
                "password changed",
                true,
                null
        ), HttpStatus.OK);
    }

    @PostMapping("/addLeaders")
    public ResponseEntity<Response> addLeaders(
            @RequestBody List<LeadersDto> leadersDto,
            @RequestHeader(name = "Authorization") String authorizationHeader
    ){
        Utilities utilities = new Utilities(jwtDecoder);
        String email = utilities.getPrincipal(authorizationHeader);
        this.userService.addLeaders(leadersDto, email);
        return new ResponseEntity<>(new Response(
                "00",
                "updated",
                true,
                null
        ), HttpStatus.OK);
    }

    @GetMapping("/contentAnalytics")
    public ResponseEntity<Response> getContentAnalytics(
            @RequestHeader(name = "Authorization") String authorizationHeader
    ){
        Utilities utilities = new Utilities(jwtDecoder);
        String email = utilities.getPrincipal(authorizationHeader);
        AnalyticsResDto analytics = this.userService.getAnalytics(email);
        Set<AnalyticsResDto> analyticSet = new HashSet<>();
        analyticSet.add(analytics);
        return new ResponseEntity<>(new Response(
                 "00", "data fetched successfully", true, analyticSet
        ), HttpStatus.OK );
    }

    @PostMapping("{type}/likeComment")
    public ResponseEntity<Response> likeComment(
            @PathVariable String type,
            @RequestParam("itemId") Optional<String> itemId,
            @RequestHeader(name = "Authorization") String authorizationHeader
    ){
        Long item = Long.parseLong(itemId.orElseThrow(
                () -> new BadRequestException("id not provided")
        ));
        Utilities utilities = new Utilities(jwtDecoder);
        String email = utilities.getPrincipal(authorizationHeader);
        this.userService.likeComment(email, item, type);
        return new ResponseEntity<>(new Response(
                "00", "liked", true, null
        ), HttpStatus.OK );
    }

    @DeleteMapping("/continueWatching/{videoId}")
    public ResponseEntity<Response> deleteWatched(
            @PathVariable String videoId,
            @RequestHeader(name = "Authorization") String authorizationHeader
    ){
        Utilities utilities = new Utilities(jwtDecoder);
        String email = utilities.getPrincipal(authorizationHeader);
        this.userService.deleteContinueWatching(Long.parseLong(videoId), email);
        return new ResponseEntity<>(new Response(
                "00", "removed form continue watching", true, null
        ), HttpStatus.OK );
    }

    @GetMapping("/continueWatching")
    public ResponseEntity<Response> getContinueWatching(
            @RequestHeader(name = "Authorization") String authorizationHeader
    ){
        Utilities utilities = new Utilities(jwtDecoder);
        String email = utilities.getPrincipal(authorizationHeader);
        Set<VideoResponseExt> videos = this.userService.getContinueWatching(email);
        return new ResponseEntity<>(
                new Response(
                    "00", "data fetched successfully", true, videos
                ), HttpStatus.OK
        );
    }

    @PostMapping("/continueWatching")
    public ResponseEntity<Response> continueWatching(
            @RequestBody ContinueWatchingDto continueWatchingDto,
            @RequestHeader(name = "Authorization") String authorizationHeader
    ){
        Utilities utilities = new Utilities(jwtDecoder);
        String email = utilities.getPrincipal(authorizationHeader);
        this.userService.continueWatching(continueWatchingDto, email);
        return new ResponseEntity<>(
                new Response(
                        "00", "created", true, null
                ), HttpStatus.OK
        );
    }

    @DeleteMapping("/deleteContent")
    public ResponseEntity<Response> deleteContent(
            @RequestParam("itemId") Optional<String> itemId,
            @RequestParam("type")  Optional<String> type
    ){
        if(type.isEmpty() && itemId.isEmpty()){
            throw new BadRequestException("type or id is not defined");
        }
        this.userService.deleteItems(itemId.get(),Long.parseLong(type.get()));
        return new ResponseEntity<>(
                new Response(
                        "00", "deleted", true, null
                ), HttpStatus.OK
        );
    }

    @GetMapping("/churchFollowed")
    public ResponseEntity<Response> getChurchesFollowed(
            @RequestHeader(name = "Authorization") String authorizationHeader
    ){
        Utilities utilities = new Utilities(jwtDecoder);
        String email = utilities.getPrincipal(authorizationHeader);
        Set<UserFollowed> church = this.userService.getFollowing(email);
        return new ResponseEntity<>(new Response(
                "00", "fetched succesfully", true, church
        ), HttpStatus.OK );
    }

    @PostMapping("/report")
    public ResponseEntity<Response> reportContent(
            @Valid @RequestBody() ReportDto reportDto,
            @RequestHeader(name = "Authorization") String authorizationHeader
    ){
        Utilities utilities = new Utilities(jwtDecoder);
        String email = utilities.getPrincipal(authorizationHeader);
        String message = this.userService.reportContent(reportDto, email);
        return new ResponseEntity<>(new Response(
                "00", message, true, null
        ), HttpStatus.OK );
    }

    @PostMapping("/feedback")
    public ResponseEntity<Response> feedback(
        @Valid @RequestBody() FeedbackDto feedbackDto,
        @RequestHeader(name = "Authorization") String authorizationHeader
    ){
        Utilities utilities = new Utilities(jwtDecoder);
        String email = utilities.getPrincipal(authorizationHeader);
        String message = this.userService.feedback(feedbackDto, email);
        return new ResponseEntity<>(new Response(
                "00", message, true, null
        ), HttpStatus.OK );
    }

}
