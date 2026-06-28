package com.leaderboard.userservice.config;

import com.leaderboard.userservice.Repository.UserRepository;
import com.leaderboard.userservice.domain.Role;
import com.leaderboard.userservice.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class AdminInitializer implements ApplicationRunner {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AdminProperties adminProperties;
    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (userRepository.existsByRole(Role.ADMIN)) {
            log.info("Admin already exists.");
            return;
        }
        User admin = User.builder()
                .username(adminProperties.getUsername())
                .email(adminProperties.getEmail())
                .password(passwordEncoder.encode(adminProperties.getPassword()))
                .role(Role.ADMIN)
                .active(true)
                .build();
        userRepository.save(admin);
        log.info("Default admin created successfully.");
    }
}
