package com.gospeltube.gospeltubebackend.controller;

import com.gospeltube.gospeltubebackend.dto.RoleDto;
import com.gospeltube.gospeltubebackend.dto.Response;
import com.gospeltube.gospeltubebackend.service.RoleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/role")
@CrossOrigin("*")
public class RoleController {
    private RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @PostMapping()
    public ResponseEntity<Response> createRole(@RequestBody RoleDto roleDto){
        RoleDto savedRole = roleService.createRole(roleDto);
        Set<RoleDto> roleSet = new HashSet<>();
        roleSet.add(savedRole);
        return new ResponseEntity<>( new Response("00", "Successful", true, roleSet ), HttpStatus.CREATED);
    }
}
