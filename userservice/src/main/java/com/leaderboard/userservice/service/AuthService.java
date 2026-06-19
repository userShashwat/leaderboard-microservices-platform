package com.leaderboard.userservice.service;

import com.leaderboard.userservice.dto.AuthResponse;
import com.leaderboard.userservice.dto.LoginRequest;
import com.leaderboard.userservice.dto.RegisterRequest;

public interface AuthService {
    AuthResponse register(RegisterRequest request);
    AuthResponse login(LoginRequest loginRequest);
}
