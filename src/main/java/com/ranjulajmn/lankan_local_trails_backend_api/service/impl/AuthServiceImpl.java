package com.ranjulajmn.lankan_local_trails_backend_api.service.impl;

import com.ranjulajmn.lankan_local_trails_backend_api.dto.auth.AuthResponse;
import com.ranjulajmn.lankan_local_trails_backend_api.dto.auth.LoginRequest;
import com.ranjulajmn.lankan_local_trails_backend_api.dto.auth.RegisterRequest;
import com.ranjulajmn.lankan_local_trails_backend_api.entity.Role;
import com.ranjulajmn.lankan_local_trails_backend_api.entity.User;
import com.ranjulajmn.lankan_local_trails_backend_api.repository.UserRepository;
import com.ranjulajmn.lankan_local_trails_backend_api.security.JwtUtil;
import com.ranjulajmn.lankan_local_trails_backend_api.service.interfaces.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Override
    public void register(RegisterRequest request) {
        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.valueOf(request.getRole()));
        user.setCreatedAt(LocalDateTime.now());

        userRepository.save(user);
    }

    @Override
    public AuthResponse login(LoginRequest request) {
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        String token = jwtUtil.generateToken(
                user.getUsername(),
                user.getRole().name()
        );

        return new AuthResponse(token);
    }
}
