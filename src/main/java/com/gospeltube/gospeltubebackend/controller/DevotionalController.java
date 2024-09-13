package com.gospeltube.gospeltubebackend.controller;


import com.gospeltube.gospeltubebackend.dto.DevotionalDto;
import com.gospeltube.gospeltubebackend.dto.DevotionalRes;
import com.gospeltube.gospeltubebackend.dto.Response;
import com.gospeltube.gospeltubebackend.entity.BookmarkDevotional;
import com.gospeltube.gospeltubebackend.entity.Devotional;
import com.gospeltube.gospeltubebackend.exception.ResourceNotFoundException;
import com.gospeltube.gospeltubebackend.service.DevotionalService;
import com.gospeltube.gospeltubebackend.util.Utilities;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1/devotional")
public class DevotionalController {

    private final DevotionalService devotionalService;
    private final JwtDecoder jwtDecoder;

    public DevotionalController(DevotionalService devotionalService, JwtDecoder jwtDecoder) {
        this.devotionalService = devotionalService;
        this.jwtDecoder = jwtDecoder;
    }

    @PostMapping
    public ResponseEntity<Response> createDevotionals(
            @Valid @RequestBody DevotionalDto devotionalDto,
            @RequestHeader(name = "Authorization") String authorizationHeader
            ){
        Utilities utilities = new Utilities(jwtDecoder);
        String email = utilities.getPrincipal(authorizationHeader);
        this.devotionalService.createDevotional(devotionalDto, email);
        return new ResponseEntity(new Response(
                "00",
                "devotional created",
                true,
                null
        ), HttpStatus.CREATED);
    }

    @GetMapping("/{devotionalId}")
    public ResponseEntity<Response> getDevotional(
            @PathVariable String devotionalId,
            @RequestHeader(name = "Authorization") String authorizationHeader
    ){
        Utilities utilities = new Utilities(jwtDecoder);
        String email = utilities.getPrincipal(authorizationHeader);
        Long id = Long.parseLong(devotionalId);
        DevotionalDto devotional = this.devotionalService.getDevotional(id, email);
        Set<DevotionalDto> setDevotional = new HashSet<>();
        setDevotional.add(devotional);

        return new ResponseEntity(new Response(
                "00",
                "data fetched successfully",
                true,
                setDevotional
        ), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Response> getDevotionals(
            @RequestParam("churchId") Optional<Long> churchId,
            @RequestHeader(name = "Authorization") String authorizationHeader
    ){
        Set<DevotionalRes> devotionals;
        Utilities utilities = new Utilities(jwtDecoder);
        String email = utilities.getPrincipal(authorizationHeader);
        if (churchId.isPresent()){
            devotionals = this.devotionalService.getDevotionals(churchId.get(), email);
        }
        else{
            devotionals = this.devotionalService.allDevotionals(email);
        }
        return new ResponseEntity<>(
                new Response(
                        "00",
                        "data fetched successfully",
                        true,
                        devotionals
                ),HttpStatus.OK
        );
    }

    @PostMapping("/bookmarkDevotional")
    public ResponseEntity<Response> bookmarkDevotional(
            @RequestParam("devotionalId") Optional<Long> devotionalId,
            @RequestHeader(name = "Authorization") String authorizationHeader
    ){
        Utilities utilities = new Utilities(jwtDecoder);
        String email = utilities.getPrincipal(authorizationHeader);
        Long devotional;
        devotional = devotionalId.orElseThrow(
                () -> new ResourceNotFoundException("blog not defined")
        );

        String message =this.devotionalService.bookmark(email, devotional);
        return new ResponseEntity<>(
                new Response(
                        "00",
                        message,
                        true,
                        null
                ),HttpStatus.OK
        );
    }

    @GetMapping("/getBookmarked")
    public ResponseEntity<Response> getBookmarked(
            @RequestHeader(name = "Authorization") String authorizationHeader
    ){
        Utilities utilities = new Utilities(jwtDecoder);
        String email = utilities.getPrincipal(authorizationHeader);
        Set<BookmarkDevotional> devotionals = this.devotionalService.getBookmarked(email);
        return new ResponseEntity<>(
                new Response(
                        "00",
                        "fetched bookmarked devotionals",
                        true,
                        devotionals
                ),HttpStatus.OK
        );
    }
}
