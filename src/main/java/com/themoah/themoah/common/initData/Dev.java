package com.themoah.themoah.common.initData;

import com.themoah.themoah.domain.auth.dto.AuthMapId;
import com.themoah.themoah.domain.auth.entity.Auth;
import com.themoah.themoah.domain.auth.entity.AuthMap;
import com.themoah.themoah.domain.auth.entity.SubAuth;
import com.themoah.themoah.domain.auth.repository.AuthMapRepository;
import com.themoah.themoah.domain.auth.repository.AuthMgmtRepository;
import com.themoah.themoah.domain.auth.repository.SubAuthMgmtRepository;
import com.themoah.themoah.domain.member.entity.Member;
import com.themoah.themoah.domain.member.repository.MemberRepository;
import com.themoah.themoah.domain.member.service.MemberService;
import com.themoah.themoah.domain.team.entity.Team;
import com.themoah.themoah.domain.team.service.TeamService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
@Profile("dev")
@Slf4j
@RequiredArgsConstructor
public class Dev {
    @Autowired
    @Lazy
    private Dev self;
    private final TeamService teamService;
    private final MemberService memberService;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthMgmtRepository authMgmtRepository;
    private final SubAuthMgmtRepository subAuthMgmtRepository;
    private final AuthMapRepository authMapRepository;

    @Bean
    @Order(2)
    @Transactional
    public ApplicationRunner initDev() {
        return args -> {
            System.out.println("initDev 시작");
            Team team = this.initTeam();
            assert team != null;

            Auth auth = this.initAuth(team);

            this.initMember(team, auth);

            System.out.println("initDev 끝");
        };
    }

    private Team initTeam() {
        if (teamService.findByTeamNm("테스트팀").isPresent())
            return null;

        return teamService.createTeam("테스트팀", "테스트팀 입니다", "En","GMT", "USD");
    }

    private Member initMember(Team team, Auth auth) {
        if (memberService.findByMemberId("admin").isPresent())
            return null;

        Member admin = Member.builder()
                .memberId("admin")
                .pwd(passwordEncoder.encode("123"))
                .memberName("관리자")
                .master(true)
                .auth(auth) // 멤버과 권한 연결
                .team(team) // 팀과 멤버 연결
                .build();

        return memberRepository.save(admin);
    }
    private Auth initAuth(Team team) {
        //메인 권한 생성
        Auth auth = Auth.builder()
                .authNm("default")
                .build();

        //메인 권한 저장
        authMgmtRepository.save(auth);
        
        
        //서브 메뉴 권한 맵핑
        Map<String, String> map = new HashMap<>();
        map.put("orderMgmt", "발주");
        map.put("estOrder", "견적");
        map.put("deliveryMgmt", "납품");
        map.put("prodMgmt", "생산");
        map.put("asnMgmt", "입고");
        map.put("dnMgmt", "출고");
        map.put("adjustMgmt", "조정");
        map.put("customerMgmt","거래처");
        map.put("gdsMgmt", "품목");
        map.put("storage", "창고");
        map.put("teamMgmt", "팀설정");
        map.put("authMgmt", "권한설정");
        map.put("comCodeMgmt", "환경설정");
        
        //서브 메뉴 리스트 생성
        List<SubAuth> subAuthList = new ArrayList<>();
        
        
        //맵핑 돌려서 key, value 값을 subAuthList에 추가
        map.forEach((key, value) -> {
            SubAuth subAuth = SubAuth.builder()
                    .subAuthNm(value)
                    .subAuthKey(key)
                    .auth(auth)
                    .build();

            subAuthList.add(subAuth);
        });

        //SubAuth 저장
        subAuthMgmtRepository.saveAll(subAuthList);

        //AuthMap에 Auth, Team 매핑정보 저장
        authMapRepository.save(AuthMap.builder()
                        .authMapId(AuthMapId.builder()
                                .teamId(team.getId())
                                .authId(auth.getAuthId())
                                .build())
                        .auth(auth)
                        .team(team)
                .build());
        

        return auth;
    }



}
