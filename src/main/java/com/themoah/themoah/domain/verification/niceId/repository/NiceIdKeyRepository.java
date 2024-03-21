package com.themoah.themoah.domain.verification.niceId.repository;

import com.themoah.themoah.domain.verification.niceId.entity.NiceIdKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NiceIdKeyRepository extends JpaRepository<NiceIdKey, String> {

}
