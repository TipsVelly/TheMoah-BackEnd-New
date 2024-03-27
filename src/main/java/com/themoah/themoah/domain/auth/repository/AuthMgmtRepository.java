package com.themoah.themoah.domain.auth.repository;

import com.themoah.themoah.domain.auth.entity.Auth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthMgmtRepository extends JpaRepository<Auth, Long> {
}
