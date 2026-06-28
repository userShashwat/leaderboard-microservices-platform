package com.leaderboard.leaderboard_service.common;

public record ApiResponse<T>(
        boolean success,
        String message,
        T data) {
}
