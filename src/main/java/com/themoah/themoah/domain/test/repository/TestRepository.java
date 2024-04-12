package com.themoah.themoah.domain.test.repository;

import com.themoah.themoah.domain.test.entity.TestEntity;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TestRepository extends JpaRepository<TestEntity, Long> {
    List<TestEntity> findAll(Sort sort);
}
