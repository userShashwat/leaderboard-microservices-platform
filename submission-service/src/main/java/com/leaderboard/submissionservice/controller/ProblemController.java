package com.leaderboard.submissionservice.controller;

import com.leaderboard.submissionservice.Exception.UnauthorizedException;
import com.leaderboard.submissionservice.common.ApiResponse;
import com.leaderboard.submissionservice.dto.Request.CreateProblemRequest;
import com.leaderboard.submissionservice.dto.Response.ProblemResponse;
import com.leaderboard.submissionservice.service.ProblemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Tag(
        name = "Problem",
        description = "Problem Management APIs"
)
@RequiredArgsConstructor
@RestController
public class ProblemController {
    private final ProblemService problemService;
    @Operation(
            summary = "Create Problem",
            description = "Adds a new problem to a contest"
    )
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
    @Operation(
            summary = "get Problem",
            description = "enter problem id to get problem information"
    )
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
