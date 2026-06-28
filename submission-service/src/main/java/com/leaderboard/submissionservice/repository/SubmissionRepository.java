package com.leaderboard.submissionservice.repository;

import com.leaderboard.submissionservice.domain.Submission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface SubmissionRepository extends JpaRepository<Submission, Long> {
    List<Submission> findByUserId(Long userId);
    List<Submission> findByProblemId(Long problemId);
    @Query("""
            SELECT s
            FROM Submission s
            JOIN FETCH s.problem p
            JOIN FETCH p.contest
            WHERE s.id = :id
            """)
    Optional<Submission> findByIdWithContest(
            @Param("id") Long id
    );
}