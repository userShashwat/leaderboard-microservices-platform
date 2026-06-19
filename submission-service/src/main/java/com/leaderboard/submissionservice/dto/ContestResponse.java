package com.leaderboard.submissionservice.dto;

import com.leaderboard.submissionservice.domain.emun.ContestStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ContestResponse {
    private Long id;

    private String name;

    private String description;

    private ContestStatus status;

    private LocalDateTime startTime;

    private LocalDateTime endTime;
}
