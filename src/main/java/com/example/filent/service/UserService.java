package com.example.filent.service;

import com.example.filent.model.dto.RegisterRequest;
import com.example.filent.model.dto.UserResponse;

public interface UserService {
    UserResponse registerUser(RegisterRequest registerRequest);
}