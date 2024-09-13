package com.gospeltube.gospeltubebackend.service;

import com.gospeltube.gospeltubebackend.dto.AlbumDto;
import com.gospeltube.gospeltubebackend.dto.AudioDto;
import com.gospeltube.gospeltubebackend.entity.Album;
import com.gospeltube.gospeltubebackend.entity.Audio;
import com.gospeltube.gospeltubebackend.entity.AudioLikes;
import com.gospeltube.gospeltubebackend.entity.Church;

import java.util.Set;

public interface AudioService {
    Audio createAudio (AudioDto audioDto, String email);
    Album createAlbum (AlbumDto albumDto, String email);
    Set<Audio> getAudioByChurch(Long churchId);
    Set<Album> getAlbumByChurch(String email);
    Audio getAudio(Long audioId);
    void addToListen(Long audioId);
    Set<Audio> getAudios();
    String createLikes(Long itemId, String email);
    Set<AudioLikes> getLikedAudio(String email);

}
