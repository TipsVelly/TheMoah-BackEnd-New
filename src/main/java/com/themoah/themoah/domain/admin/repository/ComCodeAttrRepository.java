package com.themoah.themoah.domain.admin.repository;

import com.themoah.themoah.domain.admin.entity.ComCodeAttr;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ComCodeAttrRepository extends JpaRepository<ComCodeAttr,Long> {
    List<ComCodeAttr> findByComCodeList_ComCodeId(Long comCodeId);
    @Transactional
    void deleteByComCodeAttrIdAndComCodeList_ComCodeId(Long comAttrId, Long comCodeId);

    Optional<ComCodeAttr> findByAttrNameAndComCodeList_ComCodeId(String attrName,Long comCodeId);

}
