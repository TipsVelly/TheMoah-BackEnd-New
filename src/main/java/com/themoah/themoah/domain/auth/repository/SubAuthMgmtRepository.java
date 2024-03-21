package com.themoah.themoah.domain.auth.repository;

import com.themoah.themoah.domain.auth.entity.SubAuth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubAuthMgmtRepository extends JpaRepository<SubAuth, Long> {

}
