package com.icia.weatherhelper.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;


@Configuration
@EnableWebSecurity(debug = false)
@Slf4j
public class UserSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                        .requestMatchers("/login").permitAll()
                        .requestMatchers("/login-proc").permitAll()
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/modify-profile").hasAnyRole("USER", "ADMIN")
                        .requestMatchers("/my-weather").hasAnyRole("USER", "ADMIN")
                        .requestMatchers("/my-places").hasAnyRole("USER", "ADMIN")
                        .requestMatchers("/modify-places").hasAnyRole("USER", "ADMIN")
                        .requestMatchers("/logout").hasAnyRole("USER", "ADMIN")
                        .requestMatchers("/error").permitAll()
                        .anyRequest().permitAll()
                )
                .formLogin(formLogin -> formLogin
                        .loginPage("/login")
                        .loginProcessingUrl("/login-proc")
                        .successHandler(new CustomSuccessHandler())
                        .failureUrl("/login-proc")
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                )
                .csrf(csrf -> csrf
                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                )
                .sessionManagement(session -> session
                        .invalidSessionUrl("/login")
                );

        return http.build();
    }
}
