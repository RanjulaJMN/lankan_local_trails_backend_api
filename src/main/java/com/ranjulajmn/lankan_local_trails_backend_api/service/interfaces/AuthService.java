package com.ranjulajmn.lankan_local_trails_backend_api.service.interfaces;

import com.ranjulajmn.lankan_local_trails_backend_api.dto.auth.AuthResponse;
import com.ranjulajmn.lankan_local_trails_backend_api.dto.auth.LoginRequest;
import com.ranjulajmn.lankan_local_trails_backend_api.dto.auth.RegisterRequest;

public interface AuthService {
    void register(RegisterRequest request);
    AuthResponse login(LoginRequest request);
}
