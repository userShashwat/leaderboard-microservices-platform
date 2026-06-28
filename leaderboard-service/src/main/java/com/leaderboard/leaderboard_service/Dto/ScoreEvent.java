package com.leaderboard.leaderboard_service.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScoreEvent {
    private Long submissionId;
    private Long contestId;

    private Long problemId;

    private Long userId;

    private String username;

    private Integer score;

    private String status;
}
