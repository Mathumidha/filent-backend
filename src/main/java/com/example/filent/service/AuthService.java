package com.example.filent.service;

import com.example.filent.model.dto.AuthRequest;
import com.example.filent.model.dto.AuthResponse;
import com.example.filent.model.dto.RegisterRequest;
import org.springframework.security.core.AuthenticationException;

public interface AuthService {
    AuthResponse register(RegisterRequest request);
    AuthResponse login(AuthRequest request) throws AuthenticationException;
}