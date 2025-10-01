package com.sih.SIHbackend.service;

import com.sih.SIHbackend.dto.request.LoginRequest;
import com.sih.SIHbackend.dto.request.RegisterRequest;
import com.sih.SIHbackend.dto.response.AuthResponse;
import com.sih.SIHbackend.entity.User;

public interface AuthService {
    AuthResponse login(LoginRequest request);
    AuthResponse register(RegisterRequest request);
    User findByEmail(String email);
    boolean existsByEmail(String email);
    AuthResponse logout();
}
