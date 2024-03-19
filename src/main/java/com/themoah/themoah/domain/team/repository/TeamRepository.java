package com.themoah.themoah.domain.team.repository;

import com.themoah.themoah.domain.team.entity.Team;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {
    Optional<Team> findByTeamNm(String teamNm);
}
