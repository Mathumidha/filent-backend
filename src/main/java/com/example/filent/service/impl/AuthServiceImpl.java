package com.example.filent.service.impl;

import com.example.filent.model.Role;
import com.example.filent.model.User;
import com.example.filent.model.dto.AuthRequest;
import com.example.filent.model.dto.AuthResponse;
import com.example.filent.model.dto.RegisterRequest;
import com.example.filent.repository.UserRepository;
import com.example.filent.service.AuthService;
import com.example.filent.util.JwtUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    public AuthServiceImpl(UserRepository userRepository,
                         PasswordEncoder passwordEncoder,
                         JwtUtil jwtUtil,
                         AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public AuthResponse register(RegisterRequest request) {
        // Validate email uniqueness
        if (userRepository.existsByEmail(request.email())) {
            throw new IllegalArgumentException("Email already registered");
        }

        // Validate role
        Role role;
        try {
            role = Role.valueOf(request.role().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid role specified. Valid roles are: " + 
                String.join(", ", Role.getRoleNames()));
        }

        // Create and save user
        User user = User.builder()
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .role(role)
                .fullName(request.fullName())
                .build();

        User savedUser = userRepository.save(user);
        
        // Generate JWT token
        String token = jwtUtil.generateToken(savedUser);
        return AuthResponse.builder()
                .token(token)
                .role(savedUser.getRole().name())
                .build();
    }

    @Override
    public AuthResponse login(AuthRequest request) throws AuthenticationException {
        try {
            // Authenticate credentials
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    request.email(),
                    request.password()
                )
            );
            
            // Get user details
            User user = userRepository.findByEmail(request.email())
                    .orElseThrow(() -> new BadCredentialsException("Invalid credentials"));
            
            // Generate and return token
            String token = jwtUtil.generateToken(user);
            return AuthResponse.builder()
                    .token(token)
                    .role(user.getRole().name())
                    .build();
                    
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Invalid email or password");
        }
    }
}