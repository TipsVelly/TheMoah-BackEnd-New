package com.themoah.themoah.domain.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.themoah.themoah.domain.member.entity.Member;

public interface MemberRepository extends JpaRepository<Member, String>, MemberRepositoryCustom {
    
}
