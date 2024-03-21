package com.themoah.themoah.domain.file.repository;

import com.themoah.themoah.domain.file.entity.FileStorage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileStorageRepository extends JpaRepository<FileStorage, Long> {
    List<FileStorage> findByFileGroupAndGroupId(String fileGroup, String groupId);
}