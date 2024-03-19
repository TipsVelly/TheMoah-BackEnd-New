package com.themoah.themoah.domain.admin.repository;

import com.themoah.themoah.common.initData.ComCode;
import com.themoah.themoah.domain.admin.entity.ComCodeList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ComCodeListRepository extends JpaRepository<ComCodeList,Long> {
    Optional<ComCodeList> findByGroupNm(String groupNm);

}
