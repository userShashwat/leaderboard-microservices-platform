package com.leaderboard.api_gateway.config;

import java.util.List;

public class SecurityConstants {
    public static final List<String> PUBLIC_ENDPOINTS=
            List.of(
                    "/api/users/auth/register",
                    "/api/users/auth/login"
            );
}
