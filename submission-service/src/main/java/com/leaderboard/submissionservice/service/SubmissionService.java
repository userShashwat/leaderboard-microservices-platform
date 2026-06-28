package com.leaderboard.submissionservice.service;

import com.leaderboard.submissionservice.dto.Request.CreateSubmissionRequest;
import com.leaderboard.submissionservice.dto.Response.SubmissionResponse;

import java.util.List;

public interface SubmissionService {
    SubmissionResponse submitSolution(
            Long problemId,
            Long userId,
            String username,
            CreateSubmissionRequest request
    );
    List<SubmissionResponse>
    getMySubmissions(Long userId);

    List<SubmissionResponse>
    getProblemSubmissions(Long problemId);
}
