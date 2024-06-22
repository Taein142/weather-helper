package com.icia.weatherhelper.dto;

import com.icia.weatherhelper.dto.RoleDTO;
import com.icia.weatherhelper.dto.UserDTO;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.ArrayList;
import java.util.List;

public class SecurityUserDTO extends User {

    private static final String ROLE_PREFIX = "ROLE_";
    private static final long serialVersionUID = 1L;

    public SecurityUserDTO(UserDTO userDTO, RoleDTO roleDTO) {
        super(userDTO.getUser_email(), userDTO.getUser_password(), makeGrantedAuthority(roleDTO.getRole_name()));
    }

    private static List<GrantedAuthority> makeGrantedAuthority(String role) {
        List<GrantedAuthority> list = new ArrayList<>();
        list.add(new SimpleGrantedAuthority(ROLE_PREFIX + role));
        return list;
    }
}