package com.leaderboard.leaderboard_service.Repository;

import com.leaderboard.leaderboard_service.Entity.UserProblemScore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface UserProblemScoreRepository extends JpaRepository<UserProblemScore,Long> {
    Optional<UserProblemScore>
    findByContestIdAndUserIdAndProblemId(
            Long contestId,
            Long userId,
            Long problemId
    );
}
