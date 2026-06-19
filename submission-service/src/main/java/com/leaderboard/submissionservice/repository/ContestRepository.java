package com.leaderboard.submissionservice.repository;

import com.leaderboard.submissionservice.domain.Contest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContestRepository extends JpaRepository<Contest,Long> {

}
