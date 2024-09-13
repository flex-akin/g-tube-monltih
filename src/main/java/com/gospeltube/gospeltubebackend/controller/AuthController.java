package com.gospeltube.gospeltubebackend.controller;

import com.gospeltube.gospeltubebackend.dto.*;
import com.gospeltube.gospeltubebackend.entity.Church;
import com.gospeltube.gospeltubebackend.service.FileService;
import com.gospeltube.gospeltubebackend.service.AuthService;
import com.gospeltube.gospeltubebackend.util.Utilities;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

@RestController
@Validated
@CrossOrigin("*")
@RequestMapping("api/v1/auth")
public class AuthController {
    private final AuthService authService;
    private final FileService fileService;
    private final JwtDecoder jwtDecoder;

    public AuthController(AuthService authService, FileService fileService, JwtDecoder jwtDecoder) {
        this.authService = authService;
        this.fileService = fileService;
        this.jwtDecoder = jwtDecoder;
    }

    @PostMapping
    public ResponseEntity<Response> createAppUser(@Valid  @RequestBody SignUpDto signUpDto){
        AppUserDto savedUSer = this.authService.createAppUser(signUpDto);
        Set<AppUserDto> savedAppUser = new HashSet<>();
        savedAppUser.add(savedUSer);
        return new ResponseEntity<>(new Response("00", "User Created Successfully", true, savedAppUser ), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<Response> login(@RequestBody LoginDto loginDto){
        LoginResponseDto value = authService.loginUser(loginDto.getEmail(), loginDto.getPassword(), loginDto.isOauth());
        Set<LoginResponseDto> loggedInUser = new HashSet<>();
        loggedInUser.add(value);
        return new ResponseEntity<>(new Response("00", "Login Successful", true, loggedInUser), HttpStatus.OK);
    }

    @PutMapping("/verifyEmail")
    public ResponseEntity<Response> emailVerification(@RequestBody VerifyDto verifyDto){

        boolean isVerified = this.authService.emailVerification(verifyDto.getEmail(), verifyDto.getCode());
        return new ResponseEntity<>(new Response("00", "Email verified Successfully", isVerified, null), HttpStatus.OK );
    }

    @GetMapping("/sendOtp")
    public  ResponseEntity<Response> sendOtp(@RequestParam String email) throws Exception {
        boolean isSent = this.authService.sendOtp(email);
        return new ResponseEntity<>(new Response("00", "an OTP has been sent to your email address", true, null), HttpStatus.OK );
    }

    @PostMapping("/upload")
    public ResponseEntity<Response> uploadFile(@RequestPart(value = "file") MultipartFile file){
        String url = this.fileService.uploadFile(file);
        Set<String> data = new HashSet<>();
        data.add(url);
        return new ResponseEntity<>(new Response("00", "file uploaded successfully", true, data ), HttpStatus.OK);
    }

    @PostMapping("/church")
    public ResponseEntity<Response> createChurch(@Valid @RequestBody ChurchSignUpDto churchSignUpDto){
        AppUserDto church = this.authService.createChurch(churchSignUpDto);
        Set<AppUserDto> savedAppUser = new HashSet<>();
        savedAppUser.add(church);
        return new ResponseEntity<>(new Response("00", "Church created successfully", true, savedAppUser), HttpStatus.CREATED);
    }

    @PatchMapping("/user/profilePicture")
    public ResponseEntity<Response> updateUserDp(@RequestParam String picUrl,
                                                  @RequestHeader(name = "Authorization") String authorizationHeader){
        Utilities utilities = new Utilities(jwtDecoder);
        String principal = utilities.getPrincipal(authorizationHeader);
        this.authService.updateUserDp(principal, picUrl);
        return new ResponseEntity<>(new Response("00", "profile picture set successfully", true, null), HttpStatus.OK);
    }

    @PatchMapping("/church/logo")
    public ResponseEntity<Response> updateCoyLogo(@RequestParam String logoUrl,
                                                  @RequestHeader(name = "Authorization") String authorizationHeader){
        Utilities utilities = new Utilities(jwtDecoder);
        String principal = utilities.getPrincipal(authorizationHeader);
        this.authService.updateCoyLogo(principal, logoUrl);
        return new ResponseEntity<>(new Response("00", "Church logo added successfully", true, null), HttpStatus.OK);
    }

    @PatchMapping("/church/doc")
    public ResponseEntity<Response> updateCoyDoc(@RequestParam String docUrl,
                                                  @RequestHeader(name = "Authorization") String authorizationHeader){
        Utilities utilities = new Utilities(jwtDecoder);
        String principal = utilities.getPrincipal(authorizationHeader);
        this.authService.updateCoyDoc(principal, docUrl);
        return new ResponseEntity<>(new Response("00", "Church document added successfully", true, null), HttpStatus.OK);
    }

    @GetMapping("/resetPassword/email")
    public ResponseEntity<Response> sendResetEmail(
            @RequestParam String email
    ) throws Exception {
        String message = this.authService.forgotPassword(email);
        return new ResponseEntity<>(new Response("00",message , true, null), HttpStatus.OK);
    }


}
