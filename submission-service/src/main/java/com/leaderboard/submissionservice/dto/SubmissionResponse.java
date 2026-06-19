package com.leaderboard.submissionservice.dto;

import com.leaderboard.submissionservice.domain.emun.SubmissionStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Builder
@Setter
@Getter
public class SubmissionResponse {
    private Long id;
    private Long userId;
    private String username;
    private Long problemId;
    private SubmissionStatus status;
    private Integer score;
}
