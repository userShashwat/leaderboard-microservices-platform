package com.leaderboard.submissionservice.service.impl;

import com.leaderboard.submissionservice.domain.emun.SubmissionStatus;
import com.leaderboard.submissionservice.dto.Result.JudgeResult;
import com.leaderboard.submissionservice.service.JudgeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class JudgeServiceImpl implements JudgeService {

    @Override
    public JudgeResult judge(String code) {
        if (code == null || code.isBlank()) {
            return JudgeResult.builder()
                    .status(SubmissionStatus.REJECTED)
                    .score(0)
                    .message("Code is empty")
                    .build();
        }
        if (!code.contains("class")) {
            return JudgeResult.builder()
                    .status(SubmissionStatus.REJECTED)
                    .score(0)
                    .message("Compilation Error")
                    .build();
        }
        int score = 100;

        if (code.contains("while")) {
            score = 200;
        }

        if (code.contains("for")) {
            score = 300;
        }
        System.out.println("Judge Score = " + score);
        return JudgeResult.builder()
                .status(SubmissionStatus.ACCEPTED)
                .score(score)
                .message("Accepted")
                .build();
    }
}
