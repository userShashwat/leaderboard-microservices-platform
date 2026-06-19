package com.leaderboard.userservice.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse {
    private Long userId;

    private String username;

    private String token;
}
