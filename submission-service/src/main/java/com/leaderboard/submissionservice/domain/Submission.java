package com.leaderboard.submissionservice.domain;

import com.leaderboard.submissionservice.common.BaseEntity;
import com.leaderboard.submissionservice.domain.emun.SubmissionStatus;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "submissions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Submission extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    @Column(nullable = false)
    private String username;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "problem_id", nullable = false)
    private Problem problem;
    @Column(length = 10000)
    private String code;
    private Integer score;
    @Enumerated(EnumType.STRING)
    private SubmissionStatus status;
}