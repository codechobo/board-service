package com.example.boardservice.infra.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/**").permitAll()
                .antMatchers("/h2-db/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .csrf()
                .ignoringAntMatchers("/**")
                .ignoringAntMatchers("/h2-db/**")
                .and()
                .headers()
                .addHeaderWriter(new XFrameOptionsHeaderWriter(
                        XFrameOptionsHeaderWriter.XFrameOptionsMode.SAMEORIGIN)
                )
                .frameOptions().sameOrigin()
                .and()
                .formLogin().loginPage("/")
                .defaultSuccessUrl("/").permitAll()
                .and()
                .logout()
                .logoutSuccessUrl("/").permitAll();

        return http.build();
    }
}
