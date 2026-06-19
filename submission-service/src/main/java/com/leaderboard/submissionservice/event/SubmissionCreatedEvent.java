package com.leaderboard.submissionservice.event;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SubmissionCreatedEvent {
    private Long submissionId;
}
