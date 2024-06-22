package com.icia.weatherhelper.dao;

import com.icia.weatherhelper.dto.RoleDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RoleDao {
    RoleDTO findUserByRoleId(int userRole);
}
