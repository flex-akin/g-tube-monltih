package com.gospeltube.gospeltubebackend.configuration;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class AWSConfiguration {
    @Value("${aws.accessKeyId}")
    private String accessKeyId;
    @Value("${aws.secretKey}")
    private String accessKeySecret;
    @Value("${aws.region}")
    private String bucketRegion;


    @Bean
    public AmazonS3 getAmazonS3Client(){
        final BasicAWSCredentials basicAWSCredentials = new BasicAWSCredentials(this.accessKeyId, this.accessKeySecret);
        return AmazonS3ClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(basicAWSCredentials))
                .withRegion(this.bucketRegion)
                .build();
    }


    @Bean
    public AmazonSimpleEmailService amazonSimpleEmailService(){
        final BasicAWSCredentials basicAWSCredentials = new BasicAWSCredentials(this.accessKeyId, this.accessKeySecret);
        return AmazonSimpleEmailServiceClientBuilder
                .standard()
                .withRegion(this.bucketRegion)
                .withCredentials(new AWSStaticCredentialsProvider(basicAWSCredentials))
                .build();
    }
}
