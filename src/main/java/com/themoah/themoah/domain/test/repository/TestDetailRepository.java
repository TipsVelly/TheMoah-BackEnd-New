package com.themoah.themoah.domain.test.repository;

import com.themoah.themoah.domain.test.entity.TestDetailEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TestDetailRepository extends JpaRepository<TestDetailEntity,Long> {
    List<TestDetailEntity> findByTestEntity_TestId(Long testId);
}
