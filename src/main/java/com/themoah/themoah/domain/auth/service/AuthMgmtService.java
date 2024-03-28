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
        //1. 토큰에서 회원아이디를 가져온다.
        String memberId = principal.getName();

        //2. 토큰에서 가져온 회원정보의 팀 정보를 가져온다.
        Team team = memberRepository.findById(memberId).map(Member::getTeam).orElseThrow(() -> new IllegalArgumentException("팀 정보를 가져올 수 없습니다."));

        //3. 팀정보에서 팀아이디를 추출한다.
        Long teamId = team.getId();

        //4. 추출한 팀아이디를 가지고, AuthMap에서 팀아아디를 가지고 매핑정보를 검색한다.
        List<AuthMap> allByAuthMapIdTeamId = authMapRepository.findAllByAuthMapIdTeamId(teamId);

        //5. 검색된 매핑정보 리스트에서 authNm<String> List로 변환
        List<Auth> authList = allByAuthMapIdTeamId.stream()
                .map(AuthMap::getAuth)
                .collect(Collectors.toList());


        // authList를 다시 List<AuthResponseDTO>로 변환
        List<AuthResponseDTO> responseDTOList = authList.stream().map(auth -> {
            Long authId = auth.getAuthId();
            String authNm = auth.getAuthNm();
            List<String> members = auth.getMembers().stream()
                    .filter(member -> member.getTeam().getId().equals(teamId))
                    .map(Member::getMemberId)
                    .collect(Collectors.toList());

            AuthResponseDTO authResponseDTO = AuthResponseDTO.builder()
                    .authId(authId)
                    .authNm(authNm)
                    .members(members)
                    .build();
            return authResponseDTO;
        }).collect(Collectors.toList());

        return responseDTOList;
    }

    @Transactional
    public List<AuthMemberRespnseDTO> getAuthMemberList(Principal principal) {
        // 1. 토큰에서 회원아이디를 가져온다.
        String memberId = principal.getName();

        // 2. teamId를 추출한다.
        Team team = memberRepository.findById(memberId).map(Member::getTeam).orElseThrow(() -> new IllegalArgumentException("팀 정보를 찾을 수 없습니다."));

        // 3. 해당 팀에서 속한 멤버정보를 가져온다.
        List<AuthMemberRespnseDTO> authMemberRespnseDTOList = team.getMember().stream().map(AuthMemberRespnseDTO::convert).collect(Collectors.toList());
        return authMemberRespnseDTOList;
    }

    @Transactional
    public boolean saveAuth(AuthRequestDTO authRequestDTO, Principal principal) {

        //1. 토큰에서 회원아이디를 가져온다.
        String memberId = principal.getName();

        //2. 토큰에서 가져온 회원정보의 팀 정보를 가져온다.
        Team team = memberRepository.findById(memberId).map(Member::getTeam).orElseThrow(() -> new IllegalArgumentException("팀 정보를 가져올 수 없습니다."));

        //3. 팀정보에서 팀아이디를 추출한다.
        Long teamId = team.getId();

        //4. 추출한 팀아이디를 가지고, AuthMap에서 팀아아디를 가지고 매핑정보를 검색한다.
        List<AuthMap> allByAuthMapIdTeamId = authMapRepository.findAllByAuthMapIdTeamId(teamId);

        //5. 검색된 매핑정보 리스트에서 authNm<String> List로 변환
        List<String> authNmList = allByAuthMapIdTeamId.stream()
                .map(AuthMap::getAuth)
                .map(Auth::getAuthNm)
                .collect(Collectors.toList());

        //6. 해당 리스트에서 중복된 authNm이 있다면 예외처리
        if(authNmList.contains(authRequestDTO.getAuthNm())) {
            throw new IllegalArgumentException("중복된 권한이름입니다.");
        }

        //7. 저장 로직 진행
        
        //7-1. 메인 auth 저장
        Auth auth = Auth.toEntity(authRequestDTO);
        authMgmtRepository.save(auth);
        
        
        //7-2 서브 auth 저장
        List<SubAuth> subAuthList = SubAuth.toEntityList(authRequestDTO, auth);
        subAuthMgmtRepository.saveAll(subAuthList);

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

        return true;
    }
}
