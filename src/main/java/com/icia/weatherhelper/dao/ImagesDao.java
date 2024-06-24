package com.icia.weatherhelper.dao;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ImagesDao {
    String getImageURL(int imageNum);
}
