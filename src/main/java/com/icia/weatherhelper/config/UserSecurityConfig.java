package com.icia.weatherhelper.config;

import com.icia.weatherhelper.service.CustomUserDetailsService;
import jakarta.servlet.DispatcherType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity(debug = false)
@Slf4j
public class UserSecurityConfig {

    /**
     * 정적 자원에 대한 보안 설정을 무시한다.
     * @return WebSecurityCustomizer
     */
    @Bean
    public WebSecurityCustomizer userWebSecurityCustomizer() {
        return (web) -> web.ignoring()
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

    /**
     * 사용자 인증을 위한 UserDetailsService와 PasswordEncoder를 주입받는다.
     */
    @Autowired
    public CustomUserDetailsService customUserDetailsService;

    /**
     * SecurityBeansConfig를 주입받는다.
     */
    @Autowired
    public SecurityBeansConfig securityBeansConfig;

    /**
     * 사용자 인증을 위한 UserDetailsService와 PasswordEncoder를 설정한다.
     * @param auth AuthenticationManagerBuilder
     * @throws Exception
     */
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailsService).passwordEncoder(securityBeansConfig.passwordEncoder());
    }

    /**
     * 사용자 인증을 위한 SecurityFilterChain을 설정한다.
     * @param security HttpSecurity
     * @return SecurityFilterChain
     * @throws Exception
     */
    @Bean
    @Order(2)
    public SecurityFilterChain userSecurityFilterChain(HttpSecurity security) throws Exception {
        log.info("user-securityFilterChain");
        security
                .securityMatcher("/**")
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/login").permitAll()
                        .requestMatchers("/login-proc").permitAll() // forward 허용
                        .anyRequest().hasAnyRole("USER", "ADMIN") // 사용자와 관리자 모두 접근 가능
                )
                .formLogin(formLogin -> formLogin
                        .loginPage("/login").permitAll() // 로그인 페이지
                        .loginProcessingUrl("/login-proc").permitAll() // 로그인 처리 페이지
                        .successHandler(new CustomSuccessHandler()).permitAll() // 로그인 성공시 이동 페이지
                        .failureUrl("/login-proc").permitAll() // 로그인 실패시 이동 페이지
                )
                .logout(logout -> logout.logoutUrl("/logout"))
                .userDetailsService(customUserDetailsService);
        return security.build();
    }

    /**
     * 관리자 인증을 위한 SecurityFilterChain을 설정한다.
     * @param security HttpSecurity
     * @return SecurityFilterChain
     * @throws Exception
     */
    @Bean
    @Order(1)
    public SecurityFilterChain adminSecurityFilterChain(HttpSecurity security) throws Exception {
        log.info("admin-securityFilterChain");
        security
                .securityMatcher("/admin/**")
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize -> authorize
                        .anyRequest().hasRole("ADMIN")
                )
                .formLogin(formLogin -> formLogin
                        .loginPage("/admin/login").permitAll() // 로그인 페이지
                        .loginProcessingUrl("/admin/login-proc").permitAll() // 로그인 처리 페이지
                        .successHandler(new CustomSuccessHandler()).permitAll() // 로그인 성공시 이동 페이지
                        .failureUrl("/admin/login").permitAll() // 로그인 실패시 이동 페이지
                )
                .logout(logout -> logout.logoutUrl("/admin/logout"))
                .userDetailsService(customUserDetailsService);
        return security.build();
    }

    /**
     * 기본 인증을 위한 SecurityFilterChain을 설정한다.
     * @param security HttpSecurity
     * @return SecurityFilterChain
     * @throws Exception
     */
    @Bean
    @Order(3)
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity security) throws Exception {
        security
                .securityMatcher("/**")
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize -> authorize
                        .dispatcherTypeMatchers(DispatcherType.FORWARD).permitAll()
                        .requestMatchers("/modify-profile").denyAll()
                        .requestMatchers("my-weather").denyAll()
                        .requestMatchers("my-places").denyAll()
                        .requestMatchers("modify-places").denyAll()
                        .requestMatchers("logout").denyAll()
                        .requestMatchers("/error").permitAll()
                        .anyRequest().permitAll()
                );
        return security.build();
    }
}