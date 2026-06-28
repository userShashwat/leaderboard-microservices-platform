package com.leaderboard.userservice.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@ConfigurationProperties(prefix = "default-admin")
public class AdminProperties {
    private String username;
    private String email;
    private String password;
}
