package com.gospeltube.gospeltubebackend.util;

import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class Utilities {


    private static JwtDecoder jwtDecoder = null;
    private static final int RANDOM_BOUND = 10000;
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

    public Utilities(JwtDecoder jwtDecoder) {
        this.jwtDecoder = jwtDecoder;
    }


    public static String generateOTP(){
        Random rnd = new Random();
        int number = rnd.nextInt(999999);
        return String.format("%06d", number);
    }

    public static String generateStreamName(String name){
        Random rnd = new Random();
        int number = rnd.nextInt(99999);
        long unixTimeMillis = System.currentTimeMillis();
        return unixTimeMillis + name.replace(" ", "") + number;
    }

    public String getPrincipal(String authorizationHeader){
        String jwtToken = authorizationHeader.substring("Bearer ".length());
        Jwt principal = jwtDecoder.decode(jwtToken);
        return principal.getSubject();
    }

    public static String generateUniqueReference() {
            LocalDateTime now = LocalDateTime.now();
            String timestamp = now.format(formatter);

            Random random = new Random();
            int randomNumber = random.nextInt(RANDOM_BOUND);

            return String.format("%s%04d", timestamp, randomNumber);
        }


}
