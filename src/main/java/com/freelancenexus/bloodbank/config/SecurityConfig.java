package com.freelancenexus.bloodbank.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf(csrf -> csrf.disable()) // Disable CSRF
            .authorizeHttpRequests(
                auth -> auth.anyRequest().permitAll() // Allow all requests
            ).httpBasic(httpBasic -> httpBasic.disable()) // Disable HTTP Basic Authentication
            .formLogin(formLogin -> formLogin.disable()); // Disable form login

        return http.build();

    }

}
