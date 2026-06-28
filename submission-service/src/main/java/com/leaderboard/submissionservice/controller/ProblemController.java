package com.leaderboard.submissionservice.controller;

import com.leaderboard.submissionservice.Exception.UnauthorizedException;
import com.leaderboard.submissionservice.common.ApiResponse;
import com.leaderboard.submissionservice.dto.Request.CreateProblemRequest;
import com.leaderboard.submissionservice.dto.Response.ProblemResponse;
import com.leaderboard.submissionservice.service.ProblemService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class ProblemController {
    private final ProblemService problemService;
    @PostMapping("/api/contests/{contestId}/problems")
    public ResponseEntity<ApiResponse<ProblemResponse>> createProblem(
            @RequestHeader(value = "X-User-Role", required = false) String role,
            @PathVariable Long contestId,
            @Valid @RequestBody CreateProblemRequest request
    ) {
        if(role==null || !"ADMIN".equals(role)) {
            throw new UnauthorizedException("Only ADMIN can create problems");
        }
        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Problem created successfully",
                        problemService.createProblem(contestId, request)
                )
        );
    }
    @GetMapping("/api/problems/{problemId}")
    public ResponseEntity<ApiResponse<ProblemResponse>> getProblem(@PathVariable Long problemId) {
        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Problem fetched",
                        problemService.getProblem(
                                problemId
                        )
                )
        );
    }
    @GetMapping("/api/contests/{contestId}/problems")
    public ResponseEntity<ApiResponse<List<ProblemResponse>>> getProblemsByContest(@PathVariable Long contestId) {
        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Problems fetched",
                        problemService.getProblemsByContest(contestId)
                )
        );
    }
}
