package com.icia.weatherhelper.controller;

import com.icia.weatherhelper.service.SignService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RestController
@Slf4j
public class SignRestController {
    @Autowired
    SignService signService;

    // 회원가입 메서드
    @PostMapping("sign-up")
    public String createUser(
            @RequestParam String user_email,
            @RequestParam String domain,
            @RequestParam String user_password,
            @RequestParam String user_nickname,
            @RequestParam String address,
            @RequestParam String user_detail,
            @RequestParam(defaultValue = "0") int imageNum,
            @RequestParam boolean agreeToTerms,
            RedirectAttributes rttr) {
        log.info("createUser()");

        if (!agreeToTerms) {
            rttr.addFlashAttribute("msg", "약관에 동의해주세요");
            return "redirect:/sign-up";
        }

        String[] arr = signService.createUser(user_email, domain, user_password, user_nickname, address, user_detail, imageNum);
        rttr.addFlashAttribute("msg", "회원가입 성공");

        return arr[1];
    }

    // 이메일 체크 메서드
    @PostMapping("/emailCheck")
    @ResponseBody
    public String emailCheck(@RequestParam("email") String email) {
        log.info("emailCheck()");

        int cnt = signService.emailCheck(email);

        return String.valueOf(cnt);
    }
}
