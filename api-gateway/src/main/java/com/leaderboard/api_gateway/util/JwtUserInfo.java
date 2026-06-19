package com.leaderboard.api_gateway.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class JwtUserInfo {
    private Long userId;

    private String email;

    private String role;
}
/*
 "userId": 7,
        "username": "ram",
        "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI3IiwiZW1haWwiOiJyYW1AZ21haWwuY29tIiwicm9sZSI6IlVTRVIiLCJpYXQiOjE3ODE3MTY5MTksImV4cCI6MTc4MTgwMzMxOX0.98GRBiZIGmYjBKijIQTdMknzF-8GK-Q-i0XNTE1Ytao"
    }
}
 */