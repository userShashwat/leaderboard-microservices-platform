package com.leaderboard.leaderboard_service.Entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
        name = "leaderboard",
        uniqueConstraints = @UniqueConstraint(
                columnNames = {
                        "contest_id",
                        "user_id"
                }
        )
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Leaderboard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="contest_id",nullable=false)
    private Long contestId;

    @Column(name="user_id",nullable=false)
    private Long userId;

    @Column(nullable=false)
    private String username;

    @Column(nullable=false)
    private Integer totalScore;
}
/*
                  Submission Service
                         │
                         │ ScoreEvent
                         ▼
                     Kafka Topic
                         │
                         ▼
                 Leaderboard Service
                         │
      ┌──────────────────┴──────────────────┐
      │                                     │
      ▼                                     ▼
UserProblemScore (MySQL)            Leaderboard (MySQL)
      │                                     │
      └───────────────┬─────────────────────┘
                      ▼
              Redis Sorted Set
                      ▼
                 WebSocket Push
                      ▼
                    React UI
 */
