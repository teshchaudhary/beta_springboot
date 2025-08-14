package com.example.restaurantbooking.service;

import com.example.restaurantbooking.model.User;
import com.example.restaurantbooking.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public User registerUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public Optional<User> login(String email, String rawPassword) {
        return userRepository.findByEmail(email)
                .filter(user -> passwordEncoder.matches(rawPassword, user.getPassword()));
    }
    public User save(User user) {
    return userRepository.save(user);
    
    }
    public Optional<User> findByEmail(String email) {
    return userRepository.findByEmail(email);
}
public User registerOAuth2User(String email, String name, String provider) {
    User user = User.builder()
            .email(email)
            .name(name)
            .password(UUID.randomUUID().toString()) // random password for OAuth users
            .provider(provider)
            .build();
    return userRepository.save(user);
}


}
