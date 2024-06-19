package com.icia.weatherhelper.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j
public class SignController {
    @GetMapping("sign-up")
    public String signUp(String user_email, String user_password, String user_nickname, String user_longitude, String user_latitude, String user_detail, String user_image) {



        return "signUp";
    }
}
