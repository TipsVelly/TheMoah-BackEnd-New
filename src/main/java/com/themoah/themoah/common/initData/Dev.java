package com.themoah.themoah.common.initData;
import com.themoah.themoah.domain.member.entity.Member;
import com.themoah.themoah.domain.member.repository.MemberRepository;
import com.themoah.themoah.domain.member.service.MemberService;
import com.themoah.themoah.domain.team.entity.Team;
import com.themoah.themoah.domain.team.service.TeamService;
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

    @Bean
    @Order(2)
    public ApplicationRunner initDev() {
        return args -> {
            System.out.println("initDev 시작");
            Team team = this.initTeam();
            if(team == null) return;
            this.initMember(team);
            System.out.println("initDev 끝");

        };
    }
    private Team initTeam() {

        if (teamService.findByTeamNm("테스트팀").isPresent())
            return null;


        return teamService.createTeam("테스트팀", "테스트팀 입니다", "En","GMT", "USD");


    }

    private void initMember(Team team) {
        if (memberService.findByMemberId("admin").isPresent()) return;

        Member admin = Member.builder()
                .memberId("admin")
                .pwd(passwordEncoder.encode("123"))
                .memberName("관리자")
                .master(true)
                .team(team) // 팀과 멤버 연결
                .build();

        memberRepository.save(admin);

    }



}
