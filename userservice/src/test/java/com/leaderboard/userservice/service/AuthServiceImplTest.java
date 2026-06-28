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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthServiceImplTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private AuthServiceImpl authService;

    private RegisterRequest registerRequest;
    private LoginRequest loginRequest;
    private User user;

    @BeforeEach
    void setUp() {

        registerRequest = RegisterRequest.builder()
                .username("adrita")
                .email("adrita@gmail.com")
                .password("123456")
                .build();

        loginRequest = LoginRequest.builder()
                .email("adrita@gmail.com")
                .password("123456")
                .build();

        user = User.builder()
                .id(1L)
                .username("adrita")
                .email("adrita@gmail.com")
                .password("encodedPassword")
                .role(Role.USER)
                .build();
    }

    @Test
    void register_ShouldRegisterUserSuccessfully() {

        when(userRepository.existsByEmail(registerRequest.getEmail())).thenReturn(false);
        when(userRepository.existsByUsername(registerRequest.getUsername())).thenReturn(false);
        when(passwordEncoder.encode(registerRequest.getPassword())).thenReturn("encodedPassword");
        when(jwtService.generateToken(any(User.class))).thenReturn("jwt-token");

        AuthResponse response = authService.register(registerRequest);

        assertNotNull(response);
        assertEquals("adrita", response.getUsername());
        assertEquals("jwt-token", response.getToken());

        verify(userRepository).save(any(User.class));
        verify(jwtService).generateToken(any(User.class));
    }

    @Test
    void register_ShouldThrowException_WhenEmailAlreadyExists() {

        when(userRepository.existsByEmail(registerRequest.getEmail())).thenReturn(true);

        assertThrows(
                UserAlreadyExistsException.class,
                () -> authService.register(registerRequest)
        );

        verify(userRepository, never()).save(any());
    }

    @Test
    void login_ShouldReturnToken_WhenCredentialsAreValid() {

        when(userRepository.findByEmail(loginRequest.getEmail()))
                .thenReturn(Optional.of(user));

        when(jwtService.generateToken(user))
                .thenReturn("jwt-token");

        AuthResponse response = authService.login(loginRequest);

        assertNotNull(response);
        assertEquals("jwt-token", response.getToken());
        assertEquals("adrita", response.getUsername());

        verify(authenticationManager).authenticate(
                any(UsernamePasswordAuthenticationToken.class)
        );
    }

    @Test
    void login_ShouldThrowException_WhenUserDoesNotExist() {

        when(userRepository.findByEmail(loginRequest.getEmail()))
                .thenReturn(Optional.empty());

        assertThrows(
                UserNotFoundException.class,
                () -> authService.login(loginRequest)
        );
    }
}
