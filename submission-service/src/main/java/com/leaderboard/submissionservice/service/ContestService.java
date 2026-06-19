package com.leaderboard.submissionservice.service;

import com.leaderboard.submissionservice.dto.ContestResponse;
import com.leaderboard.submissionservice.dto.CreateContestRequest;
import java.util.List;
public interface ContestService {
    ContestResponse createContest(CreateContestRequest request);
    ContestResponse getContest(Long contestId);
    List<ContestResponse> getAllContests();
}
