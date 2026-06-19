package com.leaderboard.submissionservice.domain;

import com.leaderboard.submissionservice.common.BaseEntity;
import com.leaderboard.submissionservice.domain.emun.ContestStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "contests")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class Contest extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(length = 1000)
    private String description;
    @Enumerated(EnumType.STRING)
    private ContestStatus contestStatus;
    @Enumerated(EnumType.STRING)
    private ContestStatus status;
    private LocalDateTime startTime;

    private LocalDateTime endTime;
    @OneToMany(mappedBy = "contest", cascade = CascadeType.ALL)
    private List<Problem> problems = new ArrayList<>();
}
