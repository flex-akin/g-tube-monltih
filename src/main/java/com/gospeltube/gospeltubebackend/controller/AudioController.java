package com.gospeltube.gospeltubebackend.controller;

import com.gospeltube.gospeltubebackend.dto.AlbumDto;
import com.gospeltube.gospeltubebackend.dto.AudioDto;
import com.gospeltube.gospeltubebackend.dto.Response;
import com.gospeltube.gospeltubebackend.entity.Album;
import com.gospeltube.gospeltubebackend.entity.Audio;
import com.gospeltube.gospeltubebackend.entity.AudioLikes;
import com.gospeltube.gospeltubebackend.exception.ResourceNotFoundException;
import com.gospeltube.gospeltubebackend.service.AudioService;
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
@RequestMapping("/api/v1/audio")
public class AudioController {
    private final JwtDecoder jwtDecoder;
    private final AudioService audioService;

    public AudioController(JwtDecoder jwtDecoder, AudioService audioService) {
        this.jwtDecoder = jwtDecoder;
        this.audioService = audioService;
    }

    @PostMapping
    public ResponseEntity<Response> createAudio(
            @Valid @RequestBody AudioDto audioDto,
            @RequestHeader(name = "Authorization") String authorizationHeader){
        Utilities utilities = new Utilities(jwtDecoder);
        String principal = utilities.getPrincipal(authorizationHeader);
        Audio audio = this.audioService.createAudio(audioDto, principal);
        Set<Audio> audioSet = new HashSet<>();
        audioSet.add(audio);
        return new ResponseEntity<>(new Response("00", "audio created successfully", true, audioSet ), HttpStatus.CREATED);
    }

    @PostMapping("/createAlbum")
    public ResponseEntity<Response> createAlbum(
            @Valid @RequestBody AlbumDto albumDto,
            @RequestHeader(name = "Authorization") String authorizationHeader
    ){
        Utilities utilities = new Utilities(jwtDecoder);
        String principal = utilities.getPrincipal(authorizationHeader);
        Album album = this.audioService.createAlbum(albumDto, principal);
        Set<Album> albumSet = new HashSet<>();
        albumSet.add(album);
        return new ResponseEntity<>(new Response("00", "album created successfully", true, albumSet ), HttpStatus.CREATED);
    }

    @GetMapping("/getChurchAudio")
    public ResponseEntity<Response> getAudioByChurch(
            @RequestParam("churchId") Optional<Long> churchId
    ){
        Set<Audio> audio;
        if(churchId.isPresent()){
            audio = this.audioService.getAudioByChurch(churchId.get());
        }
        else {
            audio = this.audioService.getAudios();
        }

        return new ResponseEntity<>(
                new Response(
                        "00",
                        "data fetched successfully",
                        true,
                        audio
                ),HttpStatus.OK
        );
    }

    @GetMapping("/getChurchAlbum")
    public ResponseEntity<Response> getAlbumByChurch(
            @RequestHeader(name = "Authorization") String authorizationHeader
    ){
        Utilities utilities = new Utilities(jwtDecoder);
        String principal = utilities.getPrincipal(authorizationHeader);
        Set<Album> album = this.audioService.getAlbumByChurch(principal);
        return new ResponseEntity<>(
                new Response(
                        "00",
                        "data fetched successfully",
                        true,
                        album
                ),HttpStatus.OK
        );
    }

    @GetMapping("/getAudio/{audioId}")
    public ResponseEntity<Response> getAudio(
            @PathVariable String audioId
    ){
        Long id = Long.parseLong(audioId);
        Audio audio = this.audioService.getAudio(id);
        Set<Audio> audioSet = new HashSet<>();
        audioSet.add(audio);
        return new ResponseEntity<>(
                new Response(
                        "00",
                        "data fetched successfully",
                        true,
                        audioSet
                ),HttpStatus.OK
        );
    }

    @GetMapping("/listen/{audioId}")
    public ResponseEntity<Response> listen(
            @PathVariable String audioId
    ){
        Long id = Long.parseLong(audioId);
        this.audioService.addToListen(id);
        return new ResponseEntity<>(
                new Response(
                        "00",
                        "successful",
                        true,
                        null
                ),HttpStatus.OK
        );
    }

    @GetMapping("/")
    public ResponseEntity<Response> allAudios(){
        Set<Audio> audios = this.audioService.getAudios();
        return new ResponseEntity<>(
                new Response(
                        "00",
                        "data fetch successful",
                        true,
                        audios
                ),HttpStatus.OK
        );
    }

    @PostMapping("/like")
    public ResponseEntity<Response> likeAudio(
            @RequestParam("audioId") Optional<Long> audioId,
            @RequestHeader(name = "Authorization") String authorizationHeader
    ){
        Utilities utilities = new Utilities(jwtDecoder);
        String email = utilities.getPrincipal(authorizationHeader);
        Long audio;
        audio = audioId.orElseThrow(
                () -> new ResourceNotFoundException("video not defined")
        );
        String status = this.audioService.createLikes(
                audio, email
        );
        return new ResponseEntity<>(
                new Response(
                        "00", status, true, null
                ), HttpStatus.OK
        );

    }

    @GetMapping("/getLiked")
    public ResponseEntity<Response> getLikedAudios(
            @RequestHeader(name = "Authorization") String authorizationHeader
    ){
        Utilities utilities = new Utilities(jwtDecoder);
        String email = utilities.getPrincipal(authorizationHeader);
        Set<AudioLikes> audio = this.audioService.getLikedAudio(email);
        return new ResponseEntity<>(
                new Response(
                        "00", "data fetched successfully", true, audio
                ), HttpStatus.OK
        );
    }
}
