package com.example.restaurantbooking.controller;

import com.example.restaurantbooking.model.User;
import com.example.restaurantbooking.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/oauth")
public class OAuthController {

    @Autowired
    private UserService userService;

    @GetMapping("/login-success")
    public ResponseEntity<?> oauth2LoginSuccess(OAuth2AuthenticationToken authentication) {
        OAuth2User oAuth2User = authentication.getPrincipal();
        String email = oAuth2User.getAttribute("email");
        String name = oAuth2User.getAttribute("name");

        User user = userService.findByEmail(email)
            .orElseGet(() -> {
                User newUser = User.builder()
                        .email(email)
                        .name(name)
                        .password("") // No password for OAuth
                        .build();
                return userService.save(newUser);
            });

        return ResponseEntity.ok(Map.of("message", "Logged in with Google", "user", user));
    }
}
