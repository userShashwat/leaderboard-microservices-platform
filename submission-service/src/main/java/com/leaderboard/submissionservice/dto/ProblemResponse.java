package com.leaderboard.submissionservice.dto;

import com.leaderboard.submissionservice.domain.emun.Difficulty;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProblemResponse {
    private Long id;

    private String title;

    private String description;

    private Difficulty difficulty;

    private Integer maxScore;

    private Long contestId;
    private String sampleInput;

    private String sampleOutput;
}
