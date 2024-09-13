package com.gospeltube.gospeltubebackend.dto;

public class RoleDto {
    private Integer role_id;
    private String authority;

    public Integer getRoleId() {
        return role_id;
    }

    public RoleDto(String authority, Integer role_id){
        this.authority = authority;
        this.role_id = role_id;
    }

    public RoleDto() {
    }

    public void setRoleId(Integer role_id) {
        this.role_id = role_id;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }
}
