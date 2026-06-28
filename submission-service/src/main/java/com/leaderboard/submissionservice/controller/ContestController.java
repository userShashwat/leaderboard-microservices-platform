package com.leaderboard.submissionservice.controller;

import com.leaderboard.submissionservice.Exception.UnauthorizedException;
import com.leaderboard.submissionservice.common.ApiResponse;
import com.leaderboard.submissionservice.dto.Response.ContestResponse;
import com.leaderboard.submissionservice.dto.Request.CreateContestRequest;
import com.leaderboard.submissionservice.service.ContestService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Tag(
        name = "Contest",
        description = "Contest Management APIs"
)
@RestController
@RequestMapping("/api/contests")
@RequiredArgsConstructor
public class ContestController {
    private final ContestService contestService;
    @Operation(
            summary = "Create Contest",
            description = "Creates a new contest"
    )
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
