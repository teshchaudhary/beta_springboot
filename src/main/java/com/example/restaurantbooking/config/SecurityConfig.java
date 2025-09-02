package com.example.restaurantbooking.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(
                        "/api/auth/**",
                        "/oauth2/**",
                        "/login/oauth2/**"
                ).permitAll()
                .anyRequest().authenticated()
            )
            .formLogin(form -> form.disable())   // disable default login page
            .httpBasic(httpBasic -> httpBasic.disable()) // disable basic auth
            .oauth2Login(oauth -> oauth
                .loginPage("/oauth2/authorization/google") // start Google login
                .defaultSuccessUrl("/api/auth/oauth2/success", true) // send to JSON callback
            );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
