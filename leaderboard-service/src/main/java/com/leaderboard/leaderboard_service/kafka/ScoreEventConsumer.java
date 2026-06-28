package com.leaderboard.leaderboard_service.kafka;

import com.leaderboard.leaderboard_service.Dto.ScoreEvent;
import com.leaderboard.leaderboard_service.Service.LeaderboardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Slf4j
@Component
public class ScoreEventConsumer {
    private final LeaderboardService leaderboardService;

    @KafkaListener(
            topics = "submission-events",
            groupId = "leaderboard-group"
    )
    public void consume(ScoreEvent event) {

        leaderboardService.updateLeaderboard(event);

        log.info(
                "Processed ScoreEvent for user {}",
                event.getUserId()
        );
    }
}
