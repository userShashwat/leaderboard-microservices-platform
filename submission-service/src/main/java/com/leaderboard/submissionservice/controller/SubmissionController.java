package com.leaderboard.submissionservice.controller;

import com.leaderboard.submissionservice.common.ApiResponse;
import com.leaderboard.submissionservice.dto.CreateSubmissionRequest;
import com.leaderboard.submissionservice.dto.SubmissionResponse;
import com.leaderboard.submissionservice.service.SubmissionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class SubmissionController {
    private final SubmissionService submissionService;
    @PostMapping("/api/problems/{problemId}/submit")
    public ResponseEntity<ApiResponse<SubmissionResponse>> summitSolution(
            @RequestHeader("X-User-Id") Long userId,
            @RequestHeader("X-User-Email")String username,
            @PathVariable Long problemId,
            @Valid @RequestBody CreateSubmissionRequest request

    ){
        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Solution submitted",
                        submissionService.submitSolution(problemId, userId, username, request)
                )
        );
    }
    @GetMapping("/api/submissions/me")
    public ResponseEntity<ApiResponse<List<SubmissionResponse>>> getMySubmissions(
            @RequestHeader("X-User-Id") Long userId
    ) {
        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Submissions fetched",
                        submissionService.getMySubmissions(userId)
                )
        );
    }
    @GetMapping("/api/problems/{problemId}/submissions")
    public ResponseEntity<ApiResponse<List<SubmissionResponse>>> getProblemsSubmissions(
            @PathVariable
            Long problemId
    ){
        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Problem submissions fetched",
                        submissionService
                                .getProblemSubmissions(
                                        problemId
                                )
                )
        );

    }
}
