package com.example.restaurantbooking.controller;

import com.example.restaurantbooking.entity.User;
import com.example.restaurantbooking.service.UserService;
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
        User created = userService.registerUser(user);
        return ResponseEntity.ok(Map.of("message", "User registered", "user", created));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> body) {
        String email = body.get("email");
        String password = body.get("password");

        Optional<User> user = userService.login(email, password);
        return user.map(value -> ResponseEntity.ok(Map.of("message", "Login success", "user", value)))
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

    // You can return a token or session info here later
    return ResponseEntity.ok(Map.of("message", "Logged in with Google", "user", user));
}

}
