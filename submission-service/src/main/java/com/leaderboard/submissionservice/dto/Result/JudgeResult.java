package com.leaderboard.submissionservice.dto.Result;

import com.leaderboard.submissionservice.domain.emun.SubmissionStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class JudgeResult {
    private SubmissionStatus status;
    private Integer score;
    private String message;
}
