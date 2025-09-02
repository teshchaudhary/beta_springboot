package com.example.restaurantbooking.controller;

import com.example.restaurantbooking.model.User;
import com.example.restaurantbooking.service.UserService;
import com.example.restaurantbooking.util.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;
    private final JwtUtil jwtUtil;

    public AuthController(UserService userService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody User user) {
        User saved = userService.registerUser(user);
        String token = jwtUtil.generateToken(saved.getEmail());
        return ResponseEntity.ok(Map.of("message", "User registered", "token", token));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credentials) {
        Optional<User> userOpt = userService.login(credentials.get("email"), credentials.get("password"));
        if (userOpt.isEmpty()) {
            return ResponseEntity.status(401).body(Map.of("error", "Invalid credentials"));
        }
        String token = jwtUtil.generateToken(userOpt.get().getEmail());
        return ResponseEntity.ok(Map.of("message", "Login successful", "token", token));
    }

    @GetMapping("/oauth2/success")
    public ResponseEntity<?> oauth2Success(OAuth2AuthenticationToken authentication) {
        String email = authentication.getPrincipal().getAttribute("email");
        String name = authentication.getPrincipal().getAttribute("name");

        Optional<User> existingUser = userService.findByEmail(email);

        User user = existingUser.orElseGet(() -> {
            User newUser = User.builder()
                    .email(email)
                    .name(name)
                    .password(UUID.randomUUID().toString())
                    .build();
            return userService.save(newUser);
        });

        String token = jwtUtil.generateToken(user.getEmail());

        return ResponseEntity.ok(Map.of(
                "message", "Logged in with Google",
                "user", user,
                "token", token
        ));
    }
}
