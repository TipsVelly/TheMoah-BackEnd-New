package com.themoah.themoah.domain.member.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.themoah.themoah.common.jwt.CustomUserDetails;
import com.themoah.themoah.domain.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

/**
 * CustomUserDetailsService
 */
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return memberRepository.findById(username).map(CustomUserDetails::new).get();
    }
}