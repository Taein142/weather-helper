package com.icia.weatherhelper.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserDTO {

    private Long user_id;
    private String user_email;
    private String user_password;
    private String user_nickname;
    private double user_longitude;
    private double user_latitude;
    private String user_detail;
    private String user_image;
    private String userStatus;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime createdAt;

    public UserDTO(String userEmail, String userPassword, String userNickname, Double longitude, Double latitude, String userDetail, String userImage) {
        this.user_email = userEmail;
        this.user_password = userPassword;
        this.user_nickname = userNickname;
        this.user_longitude = longitude;
        this.user_latitude = latitude;
        this.user_detail = userDetail;
        this.user_image = userImage;
    }
}