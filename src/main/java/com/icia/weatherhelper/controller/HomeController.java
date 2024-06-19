package com.icia.weatherhelper.controller;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j
public class HomeController {

    @GetMapping("/")
    public String showHomepage(HttpSession session){
        log.info("showHomepage()");


        return "home";
    }
}
