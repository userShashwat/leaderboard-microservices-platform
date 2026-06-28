package com.leaderboard.leaderboard_service;

import com.leaderboard.leaderboard_service.Dto.LeaderboardResponse;
import com.leaderboard.leaderboard_service.Dto.ScoreEvent;
import com.leaderboard.leaderboard_service.Entity.Leaderboard;
import com.leaderboard.leaderboard_service.Entity.UserProblemScore;
import com.leaderboard.leaderboard_service.Repository.LeaderboardRepository;
import com.leaderboard.leaderboard_service.Repository.UserProblemScoreRepository;
import com.leaderboard.leaderboard_service.Service.LeaderboardServiceImpl;
import com.leaderboard.leaderboard_service.Service.RedisLeaderboardService;
import com.leaderboard.leaderboard_service.websocket.LeaderboardPublisher;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class LeaderboardServiceImplTest {
    @Mock
    LeaderboardRepository leaderboardRepository;

    @Mock
    RedisLeaderboardService redisLeaderboardService;

    @Mock
    StringRedisTemplate redisTemplate;

    @Mock
    UserProblemScoreRepository userProblemScoreRepository;

    @Mock
    LeaderboardPublisher leaderboardPublisher;

    @InjectMocks
    LeaderboardServiceImpl leaderboardService;

    @Test
    void shouldCreateLeaderboardForFirstSubmission() {

        ScoreEvent event = ScoreEvent.builder()
                .contestId(1L)
                .problemId(1L)
                .userId(10L)
                .username("adrita")
                .score(100)
                .status("ACCEPTED")
                .build();

        when(userProblemScoreRepository.findByContestIdAndUserIdAndProblemId(any(), any(), any()))
                .thenReturn(Optional.empty());

        when(leaderboardRepository.findByContestIdAndUserId(any(), any()))
                .thenReturn(Optional.empty());

        when(redisLeaderboardService.getLeaderboard(any(), any()))
                .thenReturn(List.of());

        leaderboardService.updateLeaderboard(event);

        verify(userProblemScoreRepository).save(any(UserProblemScore.class));
        verify(leaderboardRepository).save(any(Leaderboard.class));
        verify(redisLeaderboardService).updateScore(any(), any(), any(), any());
    }

    @Test
    void shouldIgnoreLowerScore() {

        ScoreEvent event = ScoreEvent.builder()
                .contestId(1L)
                .problemId(1L)
                .userId(1L)
                .score(50)
                .status("ACCEPTED")
                .build();

        UserProblemScore score = UserProblemScore.builder()
                .bestScore(100)
                .build();

        when(userProblemScoreRepository.findByContestIdAndUserIdAndProblemId(any(), any(), any()))
                .thenReturn(Optional.of(score));

        leaderboardService.updateLeaderboard(event);

        verify(redisLeaderboardService, never())
                .updateScore(any(), any(), any(), any());

        verify(leaderboardPublisher, never())
                .publish(any(), any());
    }

    @Test
    void shouldUpdateLeaderboardWhenBetterScoreSubmitted() {

        ScoreEvent event = ScoreEvent.builder()
                .contestId(1L)
                .problemId(1L)
                .userId(1L)
                .username("adrita")
                .score(200)
                .status("ACCEPTED")
                .build();

        UserProblemScore score = UserProblemScore.builder()
                .bestScore(100)
                .build();

        Leaderboard leaderboard = Leaderboard.builder()
                .contestId(1L)
                .userId(1L)
                .username("adrita")
                .totalScore(100)
                .build();

        when(userProblemScoreRepository.findByContestIdAndUserIdAndProblemId(any(), any(), any()))
                .thenReturn(Optional.of(score));

        when(leaderboardRepository.findByContestIdAndUserId(any(), any()))
                .thenReturn(Optional.of(leaderboard));

        when(redisLeaderboardService.getLeaderboard(any(), any()))
                .thenReturn(List.of());

        leaderboardService.updateLeaderboard(event);

        verify(redisLeaderboardService)
                .updateScore(any(), any(), any(), any());

        verify(leaderboardPublisher)
                .publish(any(), any());
    }

    @Test
    void shouldReturnLeaderboardFromRedis() {

        List<LeaderboardResponse> responses = List.of(
                LeaderboardResponse.builder()
                        .rank(1)
                        .username("adrita")
                        .totalScore(300)
                        .build()
        );

        when(redisLeaderboardService.getLeaderboard(1L, null))
                .thenReturn(responses);

        List<LeaderboardResponse> result =
                leaderboardService.getLeaderboard(1L, null);

        assertEquals(1, result.size());

        verify(redisLeaderboardService)
                .getLeaderboard(1L, null);
    }
}
