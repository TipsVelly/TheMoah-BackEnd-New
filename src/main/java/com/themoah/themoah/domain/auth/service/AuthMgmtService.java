package com.themoah.themoah.domain.auth.service;

import com.themoah.themoah.domain.auth.dto.AuthMapId;
import com.themoah.themoah.domain.auth.dto.AuthMemberRespnseDTO;
import com.themoah.themoah.domain.auth.dto.AuthRequestDTO;
import com.themoah.themoah.domain.auth.dto.AuthResponseDTO;
import com.themoah.themoah.domain.auth.entity.Auth;
import com.themoah.themoah.domain.auth.entity.AuthMap;
import com.themoah.themoah.domain.auth.entity.SubAuth;
import com.themoah.themoah.domain.auth.repository.AuthMapRepository;
import com.themoah.themoah.domain.auth.repository.AuthMgmtRepository;
import com.themoah.themoah.domain.auth.repository.SubAuthMgmtRepository;
import com.themoah.themoah.domain.member.entity.Member;
import com.themoah.themoah.domain.member.repository.MemberRepository;
import com.themoah.themoah.domain.team.entity.Team;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthMgmtService {
    private final MemberRepository memberRepository;
    private final AuthMgmtRepository authMgmtRepository;
    private final SubAuthMgmtRepository subAuthMgmtRepository;
    private final AuthMapRepository authMapRepository;

    @Transactional
    public List<AuthResponseDTO> getAuthList(Principal principal) {
        String memberId = principal.getName();

        // 1. member 테이블에서 해당 회원의 auth 정보 가져오기

        // 2. member team 정보 가져오기
        memberRepository.findById(memberId).map(Member::getTeam).get()
                .getMember().stream()
                .map(Member::getAuth)
                .collect(Collectors.toList());



        Long authId = memberRepository.findById(memberId).map(Member::getAuth).get().getAuthId();
        if(authId == null) {
            return new ArrayList<AuthResponseDTO>();
        }
        return authMgmtRepository.findById(authId).stream()
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
    public void saveAuth(AuthRequestDTO authRequestDTO, Principal principal) {
        String memberId = principal.getName();
        Team team = memberRepository.findById(memberId).map(Member::getTeam).orElse(null);
        Long teamId;
        if(team == null) {
            teamId = -1L;
        } else {
            teamId = team.getId();
        }
        // 매핑정보 검색
        List<AuthMap> allByAuthMapIdTeamId = authMapRepository.findAllByAuthMapIdTeamId(teamId);

        Auth auth = Auth.toEntity(authRequestDTO);
        authMgmtRepository.save(auth);

        List<SubAuth> subAuthList = SubAuth.toEntityList(authRequestDTO, auth);
        subAuthMgmtRepository.saveAll(subAuthList);

        if(allByAuthMapIdTeamId.size() <= 0) {
            // 매핑정보 저장
            AuthMap authMap = AuthMap.builder()
                    .authMapId(AuthMapId.builder()
                            .authId(auth.getAuthId())
                            .teamId(teamId)
                            .build())
                    .auth(auth)
                    .team(team)
                    .build();
            authMapRepository.save(authMap);
        }
    }
}
