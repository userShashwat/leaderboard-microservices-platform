package com.leaderboard.leaderboard_service.websocket;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class LeaderboardPublisher {
    private final SimpMessagingTemplate messagingTemplate;
    public void publish(Long contestId, Object payload) {
        log.info("Publishing leaderboard to WebSocket...");
        log.info("Publishing leaderboard to contest {}", contestId);
        messagingTemplate.convertAndSend(
                "/topic/leaderboard/" + contestId,
                payload
        );
    }

}
