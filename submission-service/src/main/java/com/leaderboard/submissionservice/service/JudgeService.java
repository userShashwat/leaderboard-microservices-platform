package com.leaderboard.submissionservice.service;

import com.leaderboard.submissionservice.dto.JudgeResult;

public interface JudgeService {
    JudgeResult judge(String code);
}
