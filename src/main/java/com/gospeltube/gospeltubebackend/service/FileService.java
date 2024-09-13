package com.gospeltube.gospeltubebackend.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;

public interface FileService {
    String uploadFile( MultipartFile multipartFile);
}
