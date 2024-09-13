package com.gospeltube.gospeltubebackend.mapper;

import com.gospeltube.gospeltubebackend.dto.RoleDto;
import com.gospeltube.gospeltubebackend.entity.Role;

public class RoleMapper {
    public static RoleDto mapToRoleDto(Role role){
        return new RoleDto(
                role.getAuthority(), role.getRoleId()
        );
    }

    public static  Role mapToRoleEntity(RoleDto roleDto){
        return new Role(
                roleDto.getAuthority()
        );
    }

}
