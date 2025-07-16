package com.example.filent.service.impl;

import com.example.filent.model.Role;
import com.example.filent.model.User;
import com.example.filent.model.dto.RegisterRequest;
import com.example.filent.model.dto.UserResponse;
import com.example.filent.repository.UserRepository;
import com.example.filent.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserResponse registerUser(RegisterRequest request) {
        if (userRepository.existsByEmail(request.email())) {
            throw new IllegalArgumentException("Email already registered");
        }

        User user = User.builder()
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .role(Role.valueOf(request.role().toUpperCase()))
                .fullName(request.fullName())
                .build();

        User savedUser = userRepository.save(user);
        
        return new UserResponse(
                savedUser.getId(),
                savedUser.getEmail(),
                savedUser.getRole().name(),
                savedUser.getFullName()
        );
    }
}