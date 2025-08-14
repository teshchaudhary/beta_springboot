package com.example.restaurantbooking.controller;

import com.example.restaurantbooking.model.User;
import com.example.restaurantbooking.service.UserService;
import com.example.restaurantbooking.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody User user) {
        if (user.getEmail() == null || user.getEmail().isBlank() ||
            user.getPassword() == null || user.getPassword().isBlank()) {
            return ResponseEntity.badRequest().body(Map.of("message", "Email and password are required"));
        }

        if (userService.findByEmail(user.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body(Map.of("message", "Email already exists"));
        }

        User created = userService.registerUser(user);
        String token = JwtUtil.generateToken(created.getEmail());

        return ResponseEntity.ok(Map.of(
                "message", "User registered",
                "user", created,
                "token", token
        ));
    }

    @PostMapping("/signin")
    public ResponseEntity<?> signin(@RequestBody Map<String, String> body) {
    String email = body.get("email");
    String password = body.get("password");

    if (email == null || password == null) {
        return ResponseEntity.badRequest().body(Map.of("message", "Email and password are required"));
    }

    return userService.login(email, password)
            .map(value -> {
                String token = JwtUtil.generateToken(value.getEmail());
                return ResponseEntity.ok(Map.of(
                        "message", "Login success",
                        "user", value,
                        "token", token
                ));
            })
            .orElseGet(() -> ResponseEntity.status(401).body(Map.of("message", "Invalid credentials")));
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
                    .password("") // empty or special flag for Google
                    .build();
            return userService.save(newUser);
        });

        String token = JwtUtil.generateToken(user.getEmail());

        return ResponseEntity.ok(Map.of(
                "message", "Logged in with Google",
                "user", user,
                "token", token
        ));
    }
}
