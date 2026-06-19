package com.leaderboard.userservice.service;

import com.leaderboard.userservice.Repository.UserRepository;
import com.leaderboard.userservice.domain.Role;
import com.leaderboard.userservice.domain.User;
import com.leaderboard.userservice.dto.AuthResponse;
import com.leaderboard.userservice.dto.LoginRequest;
import com.leaderboard.userservice.dto.RegisterRequest;
import com.leaderboard.userservice.exception.UserAlreadyExistsException;
import com.leaderboard.userservice.exception.UserNotFoundException;
import com.leaderboard.userservice.security.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Slf4j
@Service
public class AuthServiceImpl implements AuthService{
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    @Override
    public AuthResponse register(RegisterRequest request) {
        if(userRepository.existsByEmail(request.getEmail())){
            throw new UserAlreadyExistsException("email already exist");
        }
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new UserAlreadyExistsException(
                    "Username already exists"
            );
        }
        User user=User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();
        userRepository.save(user);
        String token =
                jwtService.generateToken(user);
        log.info("user register {}",user.getEmail());
        return AuthResponse.builder()
                .userId(user.getId())
                .username(user.getUsername())
                .token(token)
                .build();

    }

    @Override
    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(),request.getPassword())
        );
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() ->
                        new UserNotFoundException("Invalid credentials"));

        String token = jwtService.generateToken(user);
        return AuthResponse.builder()
                .userId(user.getId())
                .username(user.getUsername())
                .token(token)
                .build();


    }
}
