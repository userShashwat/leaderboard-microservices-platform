package com.leaderboard.leaderboard_service.Controller;

import com.leaderboard.leaderboard_service.Dto.LeaderboardResponse;
import com.leaderboard.leaderboard_service.Service.LeaderboardService;
import com.leaderboard.leaderboard_service.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/leaderboard")
public class LeaderboardController {
    private final LeaderboardService leaderboardService;

    @GetMapping("/{contestId}")
    public ResponseEntity<ApiResponse<List<LeaderboardResponse>>> getLeaderboard(
            @PathVariable Long contestId,
            @RequestParam(required = false) Integer limit

    ) {
        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Leaderboard fetched",
                        leaderboardService.getLeaderboard(contestId, limit)
                )
        );
    }
    @GetMapping("/{contestId}/rank/{userId}")
    public ResponseEntity<ApiResponse<Long>> getRank(
            @PathVariable Long contestId,
            @PathVariable Long userId
    ) {
        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Rank fetched",
                        leaderboardService.getRank(contestId, userId)
                )
        );
    }
    @PostMapping("/{contestId}/cache/rebuild")
    public ResponseEntity<ApiResponse<String>> rebuild(
            @PathVariable Long contestId
    ) {
        leaderboardService.rebuildCache(contestId);
        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Redis cache rebuilt",
                        "OK"
                )
        );
    }
    @DeleteMapping("/{contestId}/cache")
    public ResponseEntity<ApiResponse<String>> clear(
            @PathVariable Long contestId

    ) {
        leaderboardService.clearCache(contestId);
        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Redis cache cleared",
                        "OK"
                )
        );
    }
}
