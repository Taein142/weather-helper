package com.icia.weatherhelper.service;

import com.icia.weatherhelper.dao.UserDao;
import com.icia.weatherhelper.dto.UserDTO;
import com.icia.weatherhelper.util.GeocoderUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@Slf4j
public class SignService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private GeocoderUtil geocoderUtil;

    public String[] createUser(String userEmail, String domain, String userPassword, String userNickname, String address, String userDetail, int imageNum) {
        log.info("createUser - service");

        String msg = "회원가입 성공";
        String view = "redirect:/";

        // 비밀번호 암호화
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        userPassword = bCryptPasswordEncoder.encode(userPassword);

        // 주소의 x,y 좌표 가져오기
        Map<String, Double> pointMap = geocoderUtil.getPointByAddress(address);
        Double longitude = pointMap.get("longitude");
        Double latitude = pointMap.get("latitude");

        // 유저 이메일에 도메인 더하기
        userEmail+="@"+domain;

        // 이미지 가져오기
        String userImage = getImageAddress(imageNum);

        UserDTO user = new UserDTO(userEmail, userPassword, userNickname, longitude, latitude, userDetail, userImage);

        try {
            userDao.createUser(user);
        } catch (Exception e) {
            e.printStackTrace();
            msg = "회원가입에 실패하였습니다.";
            view = "redirect:sign-up";
        }

        return new String[]{msg, view};
    }

    // 이미지 가져오는 메서드
    private String getImageAddress(int imageNum) {
        log.info("getImageAddress");
        return userDao.getImageAddress(imageNum);
    }

    // 이메일 체크 메서드
    public int emailCheck(String email) {
        log.info("emailCheck()");

        return userDao.emailCheck(email);
    }
}
