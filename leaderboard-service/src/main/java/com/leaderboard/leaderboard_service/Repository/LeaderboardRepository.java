package com.leaderboard.leaderboard_service.Repository;

import com.leaderboard.leaderboard_service.Entity.Leaderboard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface LeaderboardRepository extends JpaRepository<Leaderboard,Long> {
    Optional<Leaderboard>
    findByContestIdAndUserId(
            Long contestId,
            Long userId
    );

    List<Leaderboard>
    findByContestIdOrderByTotalScoreDesc(
            Long contestId
    );

}
