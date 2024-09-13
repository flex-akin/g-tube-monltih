package com.gospeltube.gospeltubebackend.mapper;

import com.gospeltube.gospeltubebackend.dto.AppUserDto;
import com.gospeltube.gospeltubebackend.entity.AppUser;

public class UserMapper {
    public static AppUserDto mapToUserDto(AppUser appUser){

        String churchLogo = null;
        if (appUser.getChurch() != null) {
            churchLogo = appUser.getChurch().getLogo();
        }
        else {
            churchLogo = appUser.getProfilePicture();
        }
        return new AppUserDto(
                appUser.getUserId(),
                appUser.getPhoneNumber(),
                appUser.getEmail(),
                appUser.getFirstName(),
                appUser.getLastName(),
                appUser.getGender(),
                appUser.getStreamIdExt(),
                appUser.getStreamId(),
                churchLogo
        );
    }
}
