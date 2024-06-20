package com.icia.weatherhelper.controller;

import com.icia.weatherhelper.service.SignService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.text.ParseException;

@Controller
@Slf4j
public class SignController {
    @Autowired
    SignService signService;

    @GetMapping("sign-up")
    public String signUp(){
        log.info("signIp()");

        return "signUp";
    }
}
