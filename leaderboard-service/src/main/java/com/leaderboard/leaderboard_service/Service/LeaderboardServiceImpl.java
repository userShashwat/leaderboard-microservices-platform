package com.leaderboard.leaderboard_service.Service;

import com.leaderboard.leaderboard_service.Dto.LeaderboardResponse;
import com.leaderboard.leaderboard_service.Dto.ScoreEvent;
import com.leaderboard.leaderboard_service.Entity.Leaderboard;
import com.leaderboard.leaderboard_service.Entity.UserProblemScore;
import com.leaderboard.leaderboard_service.Repository.LeaderboardRepository;
import com.leaderboard.leaderboard_service.Repository.UserProblemScoreRepository;
import com.leaderboard.leaderboard_service.websocket.LeaderboardPublisher;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
@Service
public class LeaderboardServiceImpl implements LeaderboardService{
    private final LeaderboardRepository leaderboardRepository;
    private final RedisLeaderboardService redisLeaderboardService;
        private final StringRedisTemplate redisTemplate;
    private final UserProblemScoreRepository userProblemScoreRepository;
    private final LeaderboardPublisher leaderboardPublisher;
    @Override
    @Transactional
    public void updateLeaderboard(ScoreEvent event) {

        log.info(
                "Received ScoreEvent -> User={}, Contest={}, Problem={}, Score={}",
                event.getUserId(),
                event.getContestId(),
                event.getProblemId(),
                event.getScore()
        );

        if (!"ACCEPTED".equals(event.getStatus())) {
            return;
        }

        Optional<UserProblemScore> scoreOptional =
                userProblemScoreRepository.findByContestIdAndUserIdAndProblemId(
                        event.getContestId(),
                        event.getUserId(),
                        event.getProblemId()
                );

        if (scoreOptional.isEmpty()) {
            handleFirstSubmission(event);
            return;
        }

        handleExistingSubmission(
                event,
                scoreOptional.get()
        );
    }
    private void handleFirstSubmission(ScoreEvent event) {

        UserProblemScore score = UserProblemScore.builder()
                .contestId(event.getContestId())
                .problemId(event.getProblemId())
                .userId(event.getUserId())
                .bestScore(event.getScore())
                .build();

        userProblemScoreRepository.save(score);

        Leaderboard leaderboard =
                leaderboardRepository
                        .findByContestIdAndUserId(
                                event.getContestId(),
                                event.getUserId()
                        )
                        .orElse(
                                Leaderboard.builder()
                                        .contestId(event.getContestId())
                                        .userId(event.getUserId())
                                        .username(event.getUsername())
                                        .totalScore(0)
                                        .build()
                        );

        leaderboard.setTotalScore(
                leaderboard.getTotalScore() + event.getScore()
        );

        leaderboardRepository.save(leaderboard);

        updateRedisAndPublish(leaderboard);

        log.info("First submission processed for user {}", event.getUserId());
    }
    private void handleExistingSubmission(
            ScoreEvent event,
            UserProblemScore existing) {

        if (existing.getBestScore() >= event.getScore()) {
            log.info(
                    "Ignoring submission. Incoming Score = {}, Current Best = {}",
                    event.getScore(),
                    existing.getBestScore()
            );
            return;
        }

        int difference = event.getScore() - existing.getBestScore();

        existing.setBestScore(event.getScore());
        userProblemScoreRepository.save(existing);

        Leaderboard leaderboard =
                leaderboardRepository.findByContestIdAndUserId(
                        event.getContestId(),
                        event.getUserId()
                ).orElseThrow(
                        () -> new RuntimeException("Leaderboard entry not found")
                );

        leaderboard.setTotalScore(
                leaderboard.getTotalScore() + difference
        );

        leaderboardRepository.save(leaderboard);

        updateRedisAndPublish(leaderboard);

        log.info("Leaderboard updated for user {}", event.getUserId());
    }
    private void updateRedisAndPublish(Leaderboard leaderboard) {

        redisLeaderboardService.updateScore(
                leaderboard.getContestId(),
                leaderboard.getUserId(),
                leaderboard.getUsername(),
                leaderboard.getTotalScore()
        );

        List<LeaderboardResponse> leaderboardList =
                redisLeaderboardService.getLeaderboard(
                        leaderboard.getContestId(),
                        null
                );

        leaderboardPublisher.publish(
                leaderboard.getContestId(),
                leaderboardList
        );

        log.info(
                "Leaderboard published for contest {}",
                leaderboard.getContestId()
        );
    }


    @Override
    public List<LeaderboardResponse> getLeaderboard(Long contestId, Integer limit) {
        List<LeaderboardResponse> leaderboard=redisLeaderboardService.getLeaderboard(
                        contestId,
                        limit
                );
        if (!leaderboard.isEmpty()) {
            log.info("Leaderboard served from Redis");
            return leaderboard;
        }
        log.info("Redis cache miss");
        rebuildCache(contestId);
        return redisLeaderboardService.getLeaderboard(
                contestId,
                limit
        );
    }

    @Override
    public Long getRank(Long contestId, Long userId) {
        return redisLeaderboardService.getRank(
                contestId,
                userId
        );
    }

    @Override
    public void rebuildCache(Long contestId) {
        List<Leaderboard> rows =
                leaderboardRepository
                        .findByContestIdOrderByTotalScoreDesc(contestId);
        for (Leaderboard row : rows) {
            redisLeaderboardService.updateScore(
                    row.getContestId(),
                    row.getUserId(),
                    row.getUsername(),
                    row.getTotalScore()
            );
        }
        log.info("Redis cache rebuilt");
    }
    @Override
    public void clearCache(Long contestId) {
        redisLeaderboardService.clearCache(contestId);
    }
}
