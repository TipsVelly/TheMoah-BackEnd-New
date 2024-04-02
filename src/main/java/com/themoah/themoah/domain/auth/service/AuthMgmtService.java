package com.themoah.themoah.domain.auth.service;

import com.themoah.themoah.domain.auth.dto.request.AuthRequestDTO;
import com.themoah.themoah.domain.auth.dto.response.AuthMemberRespnseDTO;
import com.themoah.themoah.domain.auth.dto.response.AuthResponseSelectDTO;
import com.themoah.themoah.domain.auth.dto.response.AuthResponseViewDTO;
import com.themoah.themoah.domain.auth.entity.Auth;
import com.themoah.themoah.domain.auth.entity.AuthMap;
import com.themoah.themoah.domain.auth.entity.AuthMapId;
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
    public List<AuthResponseViewDTO> getAuthList(Principal principal) {
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
        List<AuthResponseViewDTO> responseDTOList = authList.stream().map(auth -> {
            Long authId = auth.getAuthId();
            String authNm = auth.getAuthNm();
            List<String> members = auth.getMembers().stream()
                    .filter(member -> member.getTeam().getId().equals(teamId))
                    .map(Member::getMemberId)
                    .collect(Collectors.toList());

            AuthResponseViewDTO authResponseDTO = AuthResponseViewDTO.builder()
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
    @Transactional
    public boolean updateAuth(AuthRequestDTO authRequestDTO) {
        // AuthId로 권한 정보 조회
        Auth auth = authMgmtRepository.findById(authRequestDTO.getAuthId()).orElseThrow(() -> new IllegalArgumentException(("권한을 조회할 수 없습니다.")));
        List<SubAuth> subReqAuthList = SubAuth.toEntityList(authRequestDTO, auth); // request 정보
        List<SubAuth> subAuths = auth.getSubAuths(); // authId로 해당하는 subAuth List 정보

        List<Long> toDeleteIdList = new ArrayList<>();
        List<SubAuth> toAddList = new ArrayList<>();
        List<String> subAuthKeyList = subAuths.stream().map(SubAuth::getSubAuthKey).toList();

        subReqAuthList.forEach(subReqAuth -> {
            if(subAuthKeyList.contains(subReqAuth.getSubAuthKey())) {
                // 1.기존 subAuthList 요소와 일치하는 요소를 찾아서 삭제 리스트 추가합니다.
                subAuths.stream()
                        .filter(subAuth -> subAuth.getSubAuthKey().equals(subReqAuth.getSubAuthKey()))
                        .map(SubAuth::getSubAuthId)
                        .toList()
                        .forEach(toDeleteIdList::add);
            } else {
                // 2. 기존 요소에 없는 요구사항 요소를 추가합니다.
                toAddList.add(subReqAuth);
            }
        });

        subAuthMgmtRepository.deleteAllById(toDeleteIdList);
        subAuthMgmtRepository.saveAll(toAddList);

        return true;
    }

    @Transactional
    public AuthResponseSelectDTO getAuthSelectList(Long authId) {
        Auth auth = authMgmtRepository.findById(authId).orElseThrow(() -> new IllegalArgumentException("권한을 조회할 수 없습니다."));
        AuthResponseSelectDTO authResponseSelectDTO = AuthResponseSelectDTO.convert(auth);
        return authResponseSelectDTO;
    }

    @Transactional
    public boolean deleteAuth(Long authId) {
        // 권한 조회
        Auth auth = authMgmtRepository.findById(authId).orElseThrow(() -> new IllegalArgumentException("권한을 조회할 수 없습니다."));

        // 서브 권한 삭제
        List<Long> subAuthIdList = auth.getSubAuths().stream().map(SubAuth::getSubAuthId).toList();
        subAuthMgmtRepository.deleteAllById(subAuthIdList);

        // 매핑 정보 삭제(Cascade.ALL 자동삭제)

        // 권한 삭제
        Long findAuthId = auth.getAuthId();
        authMgmtRepository.deleteById(findAuthId);

        return true;
    }

    @Transactional
    public boolean changeMemberAuth(Long authId, Principal principal) {
        String memberId = principal.getName();
        
        // 멤버 조회
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new IllegalArgumentException("멤버를 조회할 수 없습니다."));
        
        // 권한 조회
        Auth auth = authMgmtRepository.findById(authId).orElseThrow(() -> new IllegalArgumentException("권한을 조회할 수 없습니다."));
        member.setAuth(auth);

        return true;
    }
}
