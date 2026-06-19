package com.leaderboard.submissionservice.listener;

import com.leaderboard.submissionservice.domain.Submission;
import com.leaderboard.submissionservice.dto.JudgeResult;
import com.leaderboard.submissionservice.event.SubmissionCreatedEvent;
import com.leaderboard.submissionservice.repository.SubmissionRepository;
import com.leaderboard.submissionservice.service.JudgeService;
import com.leaderboard.submissionservice.service.SubmissionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class SubmissionEventListener {
    private final SubmissionRepository submissionRepository;

    private final JudgeService judgeService;
    @EventListener
    @Async
    public  void handleSubmissionCreated(SubmissionCreatedEvent event){
        Submission submission=submissionRepository.findById(
                event.getSubmissionId()
        ).orElseThrow();
        JudgeResult result = judgeService.judge(submission.getCode());
        submission.setStatus(result.getStatus());
        submission.setScore(result.getScore());
        submissionRepository.save(submission);
        log.info("Submission {} judged with {}", submission.getId(), result.getStatus());
    }
}
/*
┌─────────────────────────────────────────────────────────────────────┐
│                    PROXY BEAN FACTORY                             │
│                                                                   │
│  "I create the PROXY that lets you push and move on!"           │
│                                                                   │
│  1. You publish event                                            │
│  2. Proxy catches it                                             │
│  3. Proxy says: "I'll handle this in background"                │
│  4. Proxy returns control to you IMMEDIATELY                    │
│  5. You move on to the next task                                 │
│  6. Background thread does the work                             │
│                                                                   │
│  YOU:                    PROXY:                                  │
│  "Event published!"    "Processing in background..."             │
│  "Moving on..."        "Done when ready!"                        │
│  "Returning to user"   "User doesn't wait!"                     │
│                                                                   │
└─────────────────────────────────────────────────────────────────────┘
 */
/*
User Service
    ↓
API Gateway
    ↓
Submission Service

Contest
   ↓
Problem
   ↓
Submission
   ↓
Spring Event
   ↓
Judge Listener
   ↓
Score Update

Till this asynchronous method adding
 */