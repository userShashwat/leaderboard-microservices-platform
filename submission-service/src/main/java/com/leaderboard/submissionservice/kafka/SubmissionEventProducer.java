package com.leaderboard.submissionservice.kafka;

import com.leaderboard.submissionservice.domain.ScoreEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Slf4j
@Service
public class SubmissionEventProducer {
    private final KafkaTemplate<String, ScoreEvent> kafkaTemplate;

    public void publish(ScoreEvent event){

        kafkaTemplate.send(
                "submission-events",
                event
        );

        log.info(
                "Kafka ScoreEvent published for user {}",
                event.getUserId()
        );
    }
}
