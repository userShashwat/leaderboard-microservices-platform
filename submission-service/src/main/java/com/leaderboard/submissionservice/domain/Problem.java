package com.leaderboard.submissionservice.domain;

import com.leaderboard.submissionservice.common.BaseEntity;
import com.leaderboard.submissionservice.domain.emun.Difficulty;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "problems")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Problem extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(length = 5000)
    private String description;

    @Enumerated(EnumType.STRING)
    private Difficulty difficulty;

    private Integer maxScore;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "contest_id",
            nullable = false
    )
    private Contest contest;
    @Column(length = 3000)
    private String sampleInput;

    @Column(length = 3000)
    private String sampleOutput;
    @OneToMany(mappedBy = "problem", cascade = CascadeType.ALL)
    private List<Submission> submissions = new ArrayList<>();
}
