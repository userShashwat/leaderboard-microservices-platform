package com.leaderboard.submissionservice.service.impl;

import com.leaderboard.submissionservice.Exception.ContestNotFoundException;
import com.leaderboard.submissionservice.Exception.ProblemNotFoundException;
import com.leaderboard.submissionservice.domain.Contest;
import com.leaderboard.submissionservice.domain.Problem;
import com.leaderboard.submissionservice.dto.Request.CreateProblemRequest;
import com.leaderboard.submissionservice.dto.Response.ProblemResponse;
import com.leaderboard.submissionservice.repository.ContestRepository;
import com.leaderboard.submissionservice.repository.ProblemRepository;
import com.leaderboard.submissionservice.service.ProblemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProblemServiceImpl implements ProblemService {
    private final ContestRepository contestRepository;
    private final ProblemRepository problemRepository;
    private ProblemResponse mapToResponse(Problem problem) {
        return ProblemResponse.builder()
                .id(problem.getId())
                .title(problem.getTitle())
                .description(problem.getDescription())
                .difficulty(problem.getDifficulty())
                .maxScore(problem.getMaxScore())
                .contestId(problem.getContest().getId())
                .sampleInput(problem.getSampleInput())
                .sampleOutput(problem.getSampleOutput())
                .build();
    }
    @Override
    public ProblemResponse createProblem(Long contestId, CreateProblemRequest request) {
        Contest contest = contestRepository.findById(contestId).orElseThrow(
                                () -> new ContestNotFoundException("Contest not found"));
        Problem problem = Problem.builder()
                        .title(request.getTitle())
                        .description(request.getDescription())
                        .difficulty(request.getDifficulty())
                        .maxScore(request.getMaxScore())
                        .contest(contest)
                        .sampleInput(request.getSampleInput())
                        .sampleOutput(request.getSampleOutput())
                        .build();
        problemRepository.save(problem);
        log.info("Problem created {}", problem.getTitle());
        return mapToResponse(problem);
    }

    @Override
    public List<ProblemResponse> getProblemsByContest(Long contestId) {
        return problemRepository
                .findByContestId(contestId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public ProblemResponse getProblem(Long problemId) {
        Problem problem = problemRepository.findById(problemId).orElseThrow(
                                () -> new ProblemNotFoundException("Problem not found"));
        return mapToResponse(problem);

    }
}
