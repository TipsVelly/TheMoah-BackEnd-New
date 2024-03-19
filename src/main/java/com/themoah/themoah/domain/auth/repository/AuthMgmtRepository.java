package com.themoah.themoah.domain.auth.repository;

import com.themoah.themoah.domain.auth.entity.AuthGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface AuthMgmtRepository extends JpaRepository<AuthGroup, Long> {
//    @Query(value = "SELECT "
//            + " auth_group_id AS	\"authGroupId\"	,"
//            + " group_nm AS			\"groupam\"		,"
//            + " menu_nm AS			\"menuNm\"		,"
//            + " cuser AS			\"cuser\"		,"
//            + " uuser AS			\"uuser\"		,"
//            + " cdate AS			\"cdate\"		,"
//            + " udate AS			\"udate\"		"
//            + " FROM auth_group", nativeQuery = true)
//    List<Map<String, Object>> authList();
}
