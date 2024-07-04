package com.icia.weatherhelper.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collection;

@Component
@Slf4j
public class CustomSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        log.info("Authentication successful. Handling success redirect.");

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        for (GrantedAuthority authority : authorities) {
            String role = authority.getAuthority();
            if ("ROLE_ADMIN".equals(role)) {
                log.info("Redirecting to /admin/index for ROLE_ADMIN");
                response.sendRedirect("/admin/index"); // ADMIN 권한일 경우 /admin/index로 리디렉션
                return;
            } else if ("ROLE_USER".equals(role)) {
                log.info("Redirecting to / for ROLE_USER");
                response.sendRedirect("/"); // USER 권한일 경우 /로 리디렉션
                return;
            }
        }

        // 권한에 따른 페이지가 없는 경우 기본적으로 /로 리디렉션
        log.warn("No suitable redirect found for roles. Redirecting to /");
        response.sendRedirect("/");
    }
}