package com.gospeltube.gospeltubebackend.service.impl;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.gospeltube.gospeltubebackend.exception.FileException;
import com.gospeltube.gospeltubebackend.service.FileService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;

@Service
public class FileServiceImpl implements FileService {

    private final AmazonS3 getAmazonS3Client;

    @Value("${aws.bucketName}")
    private String bucketName;

    public FileServiceImpl(AmazonS3 getAmazonS3Client) {
        this.getAmazonS3Client = getAmazonS3Client;
    }


    private File convertMultiPartFileToFile(final MultipartFile multipartFile) {
        final File file = new File(Objects.requireNonNull(multipartFile.getOriginalFilename()));
        try (final FileOutputStream outputStream = new FileOutputStream(file)) {
            outputStream.write(multipartFile.getBytes());
        } catch (IOException e) {
            throw new FileException(e.getMessage());
        }
        return file;
    }

    public String uploadFile(MultipartFile file){
        File convertedFile = convertMultiPartFileToFile(file);
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        PutObjectRequest putObjectRequest = new PutObjectRequest(this.bucketName, fileName, convertedFile);
        if (putObjectRequest.getMetadata() == null) {
            ObjectMetadata metadata = new ObjectMetadata();
            putObjectRequest.setMetadata(metadata);
        }
        putObjectRequest.getMetadata().addUserMetadata("Content-Disposition", "inline; filename=" + fileName);
        this.getAmazonS3Client.putObject(putObjectRequest);
        convertedFile.delete();
        return this.getAmazonS3Client.getUrl(this.bucketName, fileName).toString();
    }
}
