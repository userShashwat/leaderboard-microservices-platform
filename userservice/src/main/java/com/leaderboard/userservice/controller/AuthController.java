package com.leaderboard.userservice.controller;

import com.leaderboard.userservice.common.ApiResponse;
import com.leaderboard.userservice.dto.AuthResponse;
import com.leaderboard.userservice.dto.LoginRequest;
import com.leaderboard.userservice.dto.RegisterRequest;
import com.leaderboard.userservice.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<AuthResponse>> register(
            @Valid
            @RequestBody RegisterRequest request){
        return ResponseEntity.ok().body(
                new ApiResponse<>(
                        true,
                        "user registered",
                        authService.register(request)

                )
        );

    }
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponse>> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Login successful",
                        authService.login(request)
                )
        );
    }
}
