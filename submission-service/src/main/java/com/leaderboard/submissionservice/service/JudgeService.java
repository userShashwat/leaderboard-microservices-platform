package com.leaderboard.submissionservice.service;

import com.leaderboard.submissionservice.dto.Result.JudgeResult;

public interface JudgeService {
    JudgeResult judge(String code);
}
