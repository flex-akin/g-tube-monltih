package com.gospeltube.gospeltubebackend.service;

import com.gospeltube.gospeltubebackend.dto.AppUserDto;
import com.gospeltube.gospeltubebackend.dto.ChurchSignUpDto;
import com.gospeltube.gospeltubebackend.dto.LoginResponseDto;
import com.gospeltube.gospeltubebackend.dto.SignUpDto;
import com.gospeltube.gospeltubebackend.entity.Church;

public interface AuthService {
    AppUserDto createAppUser(SignUpDto signUpDto);
    AppUserDto createChurch(ChurchSignUpDto churchSignUpDto);
    boolean emailVerification(String email, String code);
    LoginResponseDto loginUser(String email, String password, boolean isOauth);
    boolean sendOtp(String email) throws Exception;
    AppUserDto updateUserDp(String email, String logoUrl);
    Church updateCoyLogo(String email, String logoUrl);
    Church updateCoyDoc(String email, String docUrl);
    String forgotPassword(String email) throws Exception;
    String changePassword (String oldPassword, String newPassword, String email);
    String resetForgotPassword(String email, String code, String password);

}