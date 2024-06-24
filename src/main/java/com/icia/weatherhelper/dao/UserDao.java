package com.icia.weatherhelper.dao;

import com.icia.weatherhelper.dto.UserDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserDao {
    void createUser(UserDTO user);

    int emailCheck(String email);

    int findRoleByEmail(String userEmail);

    <T> ScopedValue<T> findUserByEmail(String username);
}
