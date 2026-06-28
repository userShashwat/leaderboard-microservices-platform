package com.leaderboard.leaderboard_service.Entity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
        name="user_problem_score",
        uniqueConstraints = {
                @UniqueConstraint(
                        columnNames = {
                                "contest_id",
                                "user_id",
                                "problem_id"
                        }
                )
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserProblemScore {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="contest_id",nullable=false)
    private Long contestId;

    @Column(name="problem_id",nullable=false)
    private Long problemId;

    @Column(name="user_id",nullable=false)
    private Long userId;

    @Column(nullable=false)
    private Integer bestScore;
}
