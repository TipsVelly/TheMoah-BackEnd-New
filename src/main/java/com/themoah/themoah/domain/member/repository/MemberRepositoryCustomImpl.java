package com.themoah.themoah.domain.member.repository;

import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.themoah.themoah.domain.member.entity.Member;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class MemberRepositoryCustomImpl implements MemberRepositoryCustom {
   
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Member findQueryDsl() {
        // return jpaQueryFactory.selectFrom(member)
        //     .where(member.userId.eq("test@test.com"))
        //     .fetchOne();
        return null;
    }
}
