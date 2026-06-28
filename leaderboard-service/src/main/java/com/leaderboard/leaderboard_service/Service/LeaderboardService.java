package com.leaderboard.leaderboard_service.Service;

import com.leaderboard.leaderboard_service.Dto.LeaderboardResponse;
import com.leaderboard.leaderboard_service.Dto.ScoreEvent;

import java.util.List;

public interface LeaderboardService {
    void updateLeaderboard(ScoreEvent event);

    List<LeaderboardResponse> getLeaderboard(
            Long contestId,
            Integer limit
    );

    Long getRank(
            Long contestId,
            Long userId
    );

    void rebuildCache(
            Long contestId
    );

    void clearCache(
            Long contestId
    );
}
