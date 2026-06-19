package com.leaderboard.submissionservice.service.impl;

import com.leaderboard.submissionservice.Exception.ContestNotFoundException;
import com.leaderboard.submissionservice.domain.Contest;
import com.leaderboard.submissionservice.domain.emun.ContestStatus;
import com.leaderboard.submissionservice.dto.ContestResponse;
import com.leaderboard.submissionservice.dto.CreateContestRequest;
import com.leaderboard.submissionservice.repository.ContestRepository;
import com.leaderboard.submissionservice.service.ContestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ContestServiceImpl implements ContestService {
    private final ContestRepository contestRepository;
    private ContestResponse mapToResponse(Contest contest) {
        return ContestResponse.builder()
                .id(contest.getId())
                .name(contest.getName())
                .description(contest.getDescription())
                .status(contest.getStatus())
                .startTime(contest.getStartTime())
                .endTime(contest.getEndTime())
                .build();
    }
    @Override
    public ContestResponse createContest(CreateContestRequest request) {
        Contest contest= Contest.builder()
                .name(request.getName())
                .description(request.getDescription())
                .status(ContestStatus.DRAFT)
                .build();
        contestRepository.save(contest);
        log.info("Contest created {}", contest.getName());
        return mapToResponse(contest);
    }

    @Override
    public ContestResponse getContest(Long contestId) {
        Contest contest = contestRepository.findById(contestId).orElseThrow(
                                () -> new ContestNotFoundException("Contest not found"));
        return mapToResponse(contest);
    }
    @Override
    public List<ContestResponse> getAllContests() {
        return contestRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }
}
