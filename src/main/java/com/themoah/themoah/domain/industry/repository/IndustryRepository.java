package com.themoah.themoah.domain.industry.repository;

import com.themoah.themoah.domain.industry.entity.Industry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IndustryRepository extends JpaRepository<Industry, String> {
}
