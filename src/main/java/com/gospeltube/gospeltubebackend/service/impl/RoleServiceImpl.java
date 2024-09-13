package com.gospeltube.gospeltubebackend.service.impl;

import com.gospeltube.gospeltubebackend.dto.RoleDto;
import com.gospeltube.gospeltubebackend.entity.Role;
import com.gospeltube.gospeltubebackend.mapper.RoleMapper;
import com.gospeltube.gospeltubebackend.repository.RoleRepository;
import com.gospeltube.gospeltubebackend.service.RoleService;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public RoleDto createRole(RoleDto roleDto) {
        Role role = RoleMapper.mapToRoleEntity(roleDto);
        Role savedRole = this.roleRepository.save(role);
        return RoleMapper.mapToRoleDto(savedRole);
    }
}
