package com.leaderboard.leaderboard_service.Service;

import com.leaderboard.leaderboard_service.Dto.LeaderboardResponse;

import java.util.List;

public interface RedisLeaderboardService {
    void updateScore(
            Long contestId,
            Long userId,
            String username,
            Integer score
    );

    List<LeaderboardResponse> getLeaderboard(
            Long contestId,
            Integer limit
    );

    Long getRank(
            Long contestId,
            Long userId
    );

    void clearCache(Long contestId);
}
