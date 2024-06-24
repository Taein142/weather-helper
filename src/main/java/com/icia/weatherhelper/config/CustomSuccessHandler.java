package com.icia.weatherhelper.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Component
public class CustomSuccessHandler implements AuthenticationSuccessHandler {

    private static final Map<String, String> ROLE_URL_MAP = new HashMap<>() {{
        put("ROLE_USER", "/"); // USER 역할 (GrantedAuthority가 "ROLE_USER"일 때)
        put("ROLE_ADMIN", "/admin/index"); // ADMIN 역할 (GrantedAuthority가 "ROLE_ADMIN"일 때)
    }};

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        for (GrantedAuthority authority : authorities) {
            String role = authority.getAuthority();
            if (ROLE_URL_MAP.containsKey(role)) {
                response.sendRedirect(request.getContextPath() + ROLE_URL_MAP.get(role));
                return;
            }
        }
        response.sendRedirect(request.getContextPath() + "/");
    }
}