package com.themoah.themoah.domain.auth.repository;

import com.themoah.themoah.domain.auth.dto.AuthMapId;
import com.themoah.themoah.domain.auth.entity.AuthMap;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AuthMapRepository extends JpaRepository<AuthMap, AuthMapId> {
    List<AuthMap> findAllByAuthMapIdTeamId(Long teamId);
}
