package com.leaderboard.leaderboard_service.Service;

import com.leaderboard.leaderboard_service.Dto.LeaderboardResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class RedisLeaderboardServiceImpl implements RedisLeaderboardService{
    private final StringRedisTemplate redisTemplate;

    private String leaderboardKey(Long contestId) {
        return "leaderboard:contest:" + contestId;
    }

    private String usernameKey(Long contestId) {
        return "leaderboard:usernames:" + contestId;
    }
    @Override
    public void updateScore(Long contestId, Long userId, String username, Integer score) {

        redisTemplate.opsForZSet().add(
                leaderboardKey(contestId),
                userId.toString(),
                score
        );
        redisTemplate.opsForHash().put(
                usernameKey(contestId),
                userId.toString(),
                username
        );
    }

    @Override
    public List<LeaderboardResponse> getLeaderboard(Long contestId, Integer limit) {
        Set<ZSetOperations.TypedTuple<String>> entries;
        if (limit == null) {
            entries = redisTemplate.opsForZSet().reverseRangeWithScores(
                            leaderboardKey(contestId),
                            0,
                            -1
                    );
        } else {
            entries = redisTemplate.opsForZSet()
                    .reverseRangeWithScores(
                            leaderboardKey(contestId),
                            0,
                            limit - 1
                    );
        }
        List<LeaderboardResponse> response = new ArrayList<>();
        if (entries == null || entries.isEmpty()) {
            return response;
        }
        int rank = 1;
        for (ZSetOperations.TypedTuple<String> entry : entries) {
            String userId = entry.getValue();
            String username = (String)
                    redisTemplate.opsForHash().get(
                            usernameKey(contestId),
                            userId
                    );
            response.add(LeaderboardResponse.builder()
                            .rank(rank++)
                            .userId(Long.parseLong(userId))
                            .username(username)
                            .totalScore(entry.getScore().intValue())
                            .build());
        }
        return response;
    }


    @Override
    public Long getRank(Long contestId, Long userId) {
        Long rank =
                redisTemplate.opsForZSet()
                        .reverseRank(
                                leaderboardKey(contestId),
                                userId.toString()
                        );

        return rank == null ? null : rank + 1;
    }

    @Override
    public void clearCache(Long contestId) {
        redisTemplate.delete(
                leaderboardKey(contestId)
        );

        redisTemplate.delete(
                usernameKey(contestId)
        );
    }
}
