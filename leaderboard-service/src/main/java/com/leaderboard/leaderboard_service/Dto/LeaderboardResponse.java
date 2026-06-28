package com.leaderboard.leaderboard_service.Dto;

import lombok.*;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LeaderboardResponse {
    private Integer rank;

    private Long userId;

    private String username;

    private Integer totalScore;
}
