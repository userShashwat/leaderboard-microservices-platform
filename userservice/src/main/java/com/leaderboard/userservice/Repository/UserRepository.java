package com.leaderboard.userservice.Repository;

import com.leaderboard.userservice.domain.User;
import com.leaderboard.userservice.domain.Role;
import jakarta.persistence.Enumerated;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByUsername(String username);
    boolean existsByEmail(String email);

    boolean existsByUsername(String username);

    boolean existsByRole(Role role);


}
