package com.gospeltube.gospeltubebackend.service.impl;

import com.gospeltube.gospeltubebackend.dto.AlbumDto;
import com.gospeltube.gospeltubebackend.dto.AudioDto;
import com.gospeltube.gospeltubebackend.dto.NotificationDto;
import com.gospeltube.gospeltubebackend.entity.*;
import com.gospeltube.gospeltubebackend.exception.ForbiddenException;
import com.gospeltube.gospeltubebackend.exception.ResourceNotFoundException;
import com.gospeltube.gospeltubebackend.repository.*;
import com.gospeltube.gospeltubebackend.service.AudioService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
public class AudioServiceImpl implements AudioService {

    private final AlbumRepository albumRepository;
    private final AudioRepository audioRepository;
    private final UserRepository userRepository;
    private final ChurchRepository churchRepository;
    private final AudioLikesRepository audioLikesRepository;
    private final UserService userService;


    public AudioServiceImpl(AlbumRepository albumRepository, AudioRepository audioRepository, UserRepository userRepository, ChurchRepository churchRepository, AudioLikesRepository audioLikesRepository, UserService userService) {
        this.albumRepository = albumRepository;
        this.audioRepository = audioRepository;
        this.userRepository = userRepository;
        this.churchRepository = churchRepository;
        this.audioLikesRepository = audioLikesRepository;
        this.userService = userService;
    }

    @Override
    public Audio createAudio(AudioDto audioDto, String email) {
        AppUser user = this.userRepository.findByEmail(email).orElseThrow(
                () -> new ResourceNotFoundException("user not found")
        );
        Church church = this.churchRepository.findById(user.getChurch().getId()).orElseThrow(
                () -> new ForbiddenException("this user cannot this operation")
        );

        Album album;
        if (audioDto.getAlbum() != null){
            album = this.albumRepository.findById(audioDto.getAlbum()).orElseThrow(
                    () -> new ResourceNotFoundException("album doesn't exist ")
            );
        }
        else{
            album = null;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        LocalDateTime dateTime = LocalDateTime.parse(audioDto.getDate(), formatter);
        user.setTotalPosts(user.getTotalPosts() + 1L);
        this.userRepository.save(user);
        return this.audioRepository.save( new Audio(
                audioDto.getType(),
                audioDto.getAudioUrl(),
                audioDto.getImageUrl(),
                audioDto.getTitle(),
                audioDto.getArtiste(),
                audioDto.getFeaturing(),
                audioDto.getVisibility(),
                dateTime,
                album,
                church,
                audioDto.getDuration()
                )
        );
    }

    @Override
    public Album createAlbum(AlbumDto albumDto, String email) {
        AppUser user = this.userRepository.findByEmail(email).orElseThrow(
                () -> new ResourceNotFoundException("user not found")
        );
        Church church = this.churchRepository.findById(user.getChurch().getId()).orElseThrow(
                () -> new ForbiddenException("this user cannot this operation")
        );

        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        LocalDateTime dateTime = LocalDateTime.parse(albumDto.getDate(), formatter);

        return this.albumRepository.save(
                new Album(
                        church,
                        null,
                        dateTime,
                        albumDto.getImageUrl(),
                        albumDto.getArtiste(),
                        albumDto.getDescription(),
                        albumDto.getTitle()
                )
        );
    }

    @Override
    public Set<Audio> getAudioByChurch(Long churchId) {
        AppUser user = this.userRepository.findById(churchId).orElseThrow(
                () -> new ResourceNotFoundException("user not found")
        );
        Church church = this.churchRepository.findById(user.getChurch().getId()).orElseThrow(
                () -> new ResourceNotFoundException("church doesn't exit")
        );

        return this.audioRepository.findByChurch(church).orElseThrow(
                ()-> new ResourceNotFoundException("not found")
        );
    }

    @Override
    public Set<Album> getAlbumByChurch(String email) {
        AppUser user = this.userRepository.findByEmail(email).orElseThrow(
                () -> new ResourceNotFoundException("user not found")
        );
        Church church = this.churchRepository.findById(user.getChurch().getId()).orElseThrow(
                () -> new ResourceNotFoundException("church doesn't exit")
        );
        return this.albumRepository.findByChurch(church).orElseThrow(
                ()-> new ResourceNotFoundException("no album found")
        );
    }

    @Override
    public Audio getAudio(Long audioId) {
        return this.audioRepository.findById(audioId).orElseThrow(
                () -> new ResourceNotFoundException("audio not found")
        );
    }

    @Override
    public void addToListen(Long audioId) {
        Audio audio = this.audioRepository.findById(audioId).orElseThrow(
                () -> new ResourceNotFoundException("audio not found")
        );
        long newListens = audio.getListens() + 1;
        audio.setListens(newListens);
        this.audioRepository.save(audio);
    }

    @Override
    public Set<Audio> getAudios() {
         List<Audio> audioList = this.audioRepository.findAll();
         return new HashSet<>(audioList);
    }

    @Override
    public String createLikes(Long itemId, String email) {
        AppUser user = this.userRepository.findByEmail(email).orElseThrow(
                () -> new ResourceNotFoundException("user not found")
        );
        Audio audio = this.audioRepository.findById(itemId).orElseThrow(
                () -> new ResourceNotFoundException("video not found")
        );
        AppUser churchUser = this.userRepository.findByChurchId(audio.getChurch().getId()).orElseThrow(
                () -> new ResourceNotFoundException("not found")
        );
        String status = "unliked";
        Optional<AudioLikes> likedAudio =  this.audioLikesRepository.getIfLiked(user.getUserId(), itemId);
        if( likedAudio.isPresent() == true){
            audioLikesRepository.delete(likedAudio.get());
            churchUser.setTotalLikes(churchUser.getTotalLikes() - 1L);
            this.userRepository.save(churchUser);
            audio.setTotalLikes(audio.getTotalLikes() - 1L);
            this.audioRepository.save(audio);

        }
        else {
            churchUser.setTotalLikes(churchUser.getTotalLikes() + 1L);
            this.userRepository.save(churchUser);
            audio.setTotalLikes(audio.getTotalLikes() + 1L);
            this.audioRepository.save(audio);
            audioLikesRepository.save(
                    new AudioLikes(
                            audio, user
                    )
            );
            NotificationDto notificationDto = new NotificationDto(
                    user.getUserId(),
                    itemId,
                    audio.getChurch().getId(),
                    "AUDIO",
                    "liked your audio"
            );
            this.userService.notification(notificationDto);
            status = "liked";
        }
        return status;
    }

    public Set<AudioLikes> getLikedAudio(String email){
        AppUser user = this.userRepository.findByEmail(email).orElseThrow(
                () -> new ResourceNotFoundException("user not found")
        );
        return this.audioLikesRepository.findByUser(user).orElseThrow(
                () -> new ResourceNotFoundException("no liked videos")
        );
    }
}
