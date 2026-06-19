package com.leaderboard.userservice.common;

public record ApiResponse<T>(
        boolean success,
        String message,
        T data
) {
}
