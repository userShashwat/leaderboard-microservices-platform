package com.leaderboard.submissionservice.service.impl;

import com.leaderboard.submissionservice.domain.emun.SubmissionStatus;
import com.leaderboard.submissionservice.dto.JudgeResult;
import com.leaderboard.submissionservice.service.JudgeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class JudgeServiceImpl implements JudgeService {

    @Override
    public JudgeResult judge(String code) {
        if(code == null || code.isBlank()) {
            return JudgeResult.builder()
                    .status(SubmissionStatus.REJECTED)
                    .score(0)
                    .message("Code is empty")
                    .build();
        }
        if(code.contains("class")) {
            return JudgeResult.builder()
                    .status(SubmissionStatus.ACCEPTED)
                    .score(100)
                    .message("Accepted")
                    .build();
        }
        return JudgeResult.builder()
                .status(SubmissionStatus.REJECTED)
                .score(0)
                .message("Compilation Error")
                .build();
    }
}
