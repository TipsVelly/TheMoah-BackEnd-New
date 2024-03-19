package com.themoah.themoah.domain.member.service;

import java.lang.annotation.Annotation;
import java.util.Optional;

import org.springframework.core.annotation.MergedAnnotation;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.themoah.themoah.common.security.TokenProvider;
import com.themoah.themoah.domain.member.dto.RequestMemberDto;
import com.themoah.themoah.domain.member.dto.ResponseMemberDto;
import com.themoah.themoah.domain.member.entity.Member;
import com.themoah.themoah.domain.member.repository.MemberRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository  memberRepository;
    private final PasswordEncoder   passwordEncoder;
    private final TokenProvider     tokenProvider;
    
    @Transactional
    public boolean initPassword(String id) {
        Optional<Member> member = memberRepository.findById(id);
        member.ifPresent(
                m -> m.setPwd(passwordEncoder.encode("1234"))
        );

        return true;
    }

    @Transactional
    public ResponseMemberDto verify(String token) {

        Authentication authentication = tokenProvider.getAuthentication(token);
        Authentication verifiedAuthentication = tokenProvider.authenticate(authentication);

        String userId = tokenProvider.extractUserId(verifiedAuthentication);
        return memberRepository.findById(userId).map(ResponseMemberDto::generate).get();

    }

    @Transactional
    public boolean addMember(RequestMemberDto pMember) {
        boolean rst = true;
        memberRepository.save(Member.builder()
                .memberId(pMember.getMemberId())
                .pwd(passwordEncoder.encode(pMember.getPassword()))
                .memberName(pMember.getMemberName())
                .build());
        return rst;
    }

    public Optional<Member> findByMemberId(String memberId) {
        return memberRepository.findById(memberId);
    }
}