package com.leaderboard.submissionservice.service;

import com.leaderboard.submissionservice.dto.CreateProblemRequest;
import com.leaderboard.submissionservice.dto.ProblemResponse;

import java.util.List;

public interface ProblemService {
    ProblemResponse createProblem(Long contestId, CreateProblemRequest request);
    List<ProblemResponse> getProblemsByContest(Long contestId);
    ProblemResponse getProblem(Long problemId);

}
