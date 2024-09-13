package com.gospeltube.gospeltubebackend.controller;

import com.gospeltube.gospeltubebackend.dto.AdminNotificationDto;
import com.gospeltube.gospeltubebackend.dto.ChurchEnableOFreeze;
import com.gospeltube.gospeltubebackend.dto.ChurchFAdmin;
import com.gospeltube.gospeltubebackend.dto.Response;
import com.gospeltube.gospeltubebackend.entity.Admin;
import com.gospeltube.gospeltubebackend.exception.BadRequestException;
import com.gospeltube.gospeltubebackend.service.AdminService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/admin")
@CrossOrigin("*")
public class AdminController {
    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping("/activate/freeze")
    public ResponseEntity<Response> activateUser(
            @RequestParam Optional<String> churchId
            ){
        Long id = null;
        if(churchId.isPresent()){
            id = Long.parseLong(churchId.get());
        }
        else {
            throw new BadRequestException("provide church id");
        }
        String message = this.adminService.activateUser(id);
        return new ResponseEntity<>(
                new Response(
                        "00", message, true, null), HttpStatus.OK
        );
    }

    @GetMapping("/getSingleChurch")
    public ResponseEntity<Response> getSingleChurch(
            @RequestParam Optional<String> churchId
    ){
        Long id = null;
        if(churchId.isPresent()){
            id = Long.parseLong(churchId.get());
        }
        else {
            throw new BadRequestException("provide church id");
        }
        ChurchFAdmin church = this.adminService.singleChurch(id);
        Set<ChurchFAdmin> churchSet = new HashSet<>();
        churchSet.add(church);
        return new ResponseEntity( new Response(
                "00", "data fetched successfully",
                true, churchSet), HttpStatus.OK);
    }

    @GetMapping("/churchEnableData")
    public ResponseEntity<Response>  getSingleChurch(){
        Set<ChurchEnableOFreeze> church = this.adminService.churchesEnable();
        return  new ResponseEntity( new Response(
                "00", "data fetched successfully",
                true, church), HttpStatus.OK);
    }

    @PostMapping("/postNotification")
    public ResponseEntity<Response> postNotification(
            @RequestBody @Valid AdminNotificationDto adminNotificationDto
            ){
        this.adminService.postNotification(adminNotificationDto);
        return new ResponseEntity( new Response(
                "00", "the notification has been created",
                true, null
        ), HttpStatus.CREATED);
    }

    @GetMapping("/getStatistic")
    public  ResponseEntity<Response> getStatistics(){
        Admin data = this.adminService.getStatistics();
        Set<Admin> dataset = new HashSet<>();
        dataset.add(data);
        return new ResponseEntity( new Response(
                "00", "data fetched successfully",
                true, dataset
        ), HttpStatus.OK);

    }
}
