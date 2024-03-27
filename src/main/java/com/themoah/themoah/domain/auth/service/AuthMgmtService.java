package com.themoah.themoah.domain.auth.service;

import com.themoah.themoah.domain.auth.dto.AuthRequestDTO;
import com.themoah.themoah.domain.auth.dto.AuthMemberRespnseDTO;
import com.themoah.themoah.domain.auth.dto.AuthResponseDTO;
import com.themoah.themoah.domain.auth.entity.Auth;
import com.themoah.themoah.domain.auth.entity.SubAuth;
import com.themoah.themoah.domain.auth.repository.AuthMgmtRepository;
import com.themoah.themoah.domain.auth.repository.SubAuthMgmtRepository;
import com.themoah.themoah.domain.member.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthMgmtService {
    private final MemberRepository memberRepository;
    private final AuthMgmtRepository authMgmtRepository;
    private final SubAuthMgmtRepository subAuthMgmtRepository;

    @Transactional
    public List<AuthMemberRespnseDTO> getAuthMemberList(Long teamId) {
        return memberRepository.findAllByTeamId(teamId).stream()
                .map(AuthMemberRespnseDTO::convert)
                .collect(Collectors.toList());
    }

    @Transactional
    public List<AuthResponseDTO> getAuthList(Principal principal) {
        String memberId = principal.getName();
        Long teamId = memberRepository.findById(memberId).map(member -> member.getTeam() == null ? null : member.getTeam().getId()).orElse(null);
        if(teamId == null) {
            return new ArrayList<AuthResponseDTO>();
        }
        return authMgmtRepository.findAllByTeamId(teamId).stream()
                .map(AuthResponseDTO::convert)
                .collect(Collectors.toList());
    }

    @Transactional
    public List<AuthMemberRespnseDTO> getAuthMemberList(Principal principal) {
        String memberId = principal.getName();
        List<AuthMemberRespnseDTO> rst;

        Long teamId = memberRepository.findById(memberId).map(member -> {
            return member.getTeam() != null ? member.getTeam().getId() : null;
        }).orElse(null);

        if(teamId == null) {
            rst = memberRepository.findById(memberId).stream().map(member -> {
                AuthMemberRespnseDTO authMemberRespnseDTO = AuthMemberRespnseDTO.builder()
                        .memberId(member.getMemberId())
                        .memberNm(member.getMemberName())
                        .build();
                return authMemberRespnseDTO;
            }).toList();
        } else {
            rst = memberRepository.findAllByTeamId(teamId).stream()
                    .map(AuthMemberRespnseDTO::convert)
                    .toList();
        }
        return rst;
    }

    @Transactional
    public List<AuthMemberRespnseDTO> getAuthList() {
        return authMgmtRepository.findAll().stream()
                .map(AuthMemberRespnseDTO::convert)
                .toList();
    }

    @Transactional
    public void saveAuth(AuthRequestDTO authRequestDTO) {
        Auth auth = Auth.toEntity(authRequestDTO);
        authMgmtRepository.save(auth);

        List<SubAuth> subAuthList = SubAuth.toEntityList(authRequestDTO, auth);
        subAuthMgmtRepository.saveAll(subAuthList);
    }


    public void initAuth() {
        Auth auth = Auth.builder()
                .authNm("관리자")
                .build();

        Map<String, String> map = new HashMap<>();
        map.put("orderMgmt", "발주");
        map.put("estOrder", "견적");
        map.put("deliveryMgmt", "납품");
        map.put("prodMgmt", "생산");
        map.put("asnMgmt", "입고");
        map.put("dnMgmt", "출고");
        map.put("ajustMgmt", "조정");
        map.put("customerMgmt","거래처");

    }
}
