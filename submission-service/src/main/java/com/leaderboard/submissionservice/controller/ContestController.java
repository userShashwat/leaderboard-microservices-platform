package com.leaderboard.submissionservice.controller;

import com.leaderboard.submissionservice.Exception.UnauthorizedException;
import com.leaderboard.submissionservice.common.ApiResponse;
import com.leaderboard.submissionservice.dto.ContestResponse;
import com.leaderboard.submissionservice.dto.CreateContestRequest;
import com.leaderboard.submissionservice.service.ContestService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contests")
@RequiredArgsConstructor
public class ContestController {
    private final ContestService contestService;
    @PostMapping
    public ResponseEntity<ApiResponse<ContestResponse>> createContest(@RequestHeader ("X-User-Role")String role, @Valid @RequestBody CreateContestRequest contestRequest){
        if(!"ADMIN".equals(role)) {
            throw new UnauthorizedException("Only ADMIN can create contests");
        }
        ContestResponse response = contestService.createContest(contestRequest);
        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Contest created successfully",
                        response
                )
        );

    }
    @GetMapping("/{contestId}")
    public ResponseEntity<ApiResponse<ContestResponse>> getContest(@PathVariable Long contestId) {
        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Contest fetched",
                        contestService.getContest(contestId)
                )
        );
    }
    @GetMapping
    public ResponseEntity<ApiResponse<List<ContestResponse>>> getAllContests() {
        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Contest list fetched",
                        contestService.getAllContests()
                )
        );
    }
}
