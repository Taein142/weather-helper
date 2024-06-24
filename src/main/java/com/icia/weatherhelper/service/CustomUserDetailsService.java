package com.icia.weatherhelper.service;

import com.icia.weatherhelper.dao.RoleDao;
import com.icia.weatherhelper.dao.UserDao;
import com.icia.weatherhelper.dto.RoleDTO;
import com.icia.weatherhelper.dto.SecurityUserDTO;
import com.icia.weatherhelper.dto.UserDTO;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {

    @Setter(onMethod_ = {@Autowired})
    private UserDao userDao;
    @Setter(onMethod_ = {@Autowired})
    private RoleDao roleDao;

    public CustomUserDetailsService() {
        log.info("CustomUserDetailsServiceForUser");
    }

    @Override
    public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {
        log.info("loadUserByUsername");

        int roleID = userDao.findRoleByEmail(userEmail);
        RoleDTO roleDTO = roleDao.findUserByRoleId(roleID);

        UserDTO userDTO = (UserDTO) userDao.findUserByEmail(userEmail)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + userEmail));

        return new SecurityUserDTO(userDTO, roleDTO);

    }
}