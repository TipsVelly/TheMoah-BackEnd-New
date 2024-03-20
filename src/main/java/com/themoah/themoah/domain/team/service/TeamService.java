package com.themoah.themoah.domain.team.service;

import com.themoah.themoah.domain.file.service.FileStorageService;
import com.themoah.themoah.domain.member.entity.Member;
import com.themoah.themoah.domain.member.repository.MemberRepository;
import com.themoah.themoah.domain.team.dto.TeamSettingDto;
import com.themoah.themoah.domain.team.entity.Team;
import com.themoah.themoah.domain.team.repository.TeamRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TeamService {

    private final MemberRepository memberRepository;
    private final TeamRepository teamRepository;
    private final FileStorageService fileStorageService;

    @Transactional
    public void saveTeamSettings(TeamSettingDto teamSettingDto, String memberId) {
        memberRepository.findById(memberId).ifPresent(member -> {
            if (member.getTeam() != null) {
                System.out.println("1-1");
                // 기존 팀 정보 업데이트
                member.getTeam().updateTeamInfo(
                        teamSettingDto.getTeamNm(),
                        teamSettingDto.getTeamInfo(),
                        teamSettingDto.getTimeZone(),
                        teamSettingDto.getCurrency(),
                        teamSettingDto.getLanguage()
                );
            } else {
                // 새 팀 생성 및 설정
                Team team = teamRepository.save(Team.builder()
                        .teamNm(teamSettingDto.getTeamNm())
                        .teamInfo(teamSettingDto.getTeamInfo())
                        .timeZone(teamSettingDto.getTimeZone())
                        .currency(teamSettingDto.getCurrency())
                        .language(teamSettingDto.getLanguage())
                        .build());
                member.setTeam(team);
            }
        });
    }

    public Team createTeam(String teamNm, String teamInfo, String language, String timeZone, String currency) {

        Team team = Team.builder()
                .teamNm(teamNm)
                .teamInfo(teamInfo)
                .timeZone(timeZone)
                .currency(currency)
                .language(language)
                .build();
        teamRepository.save(team);
        return team;
    }
    public Optional<Team> findByTeamNm(String teamNm) {
        return teamRepository.findByTeamNm(teamNm);
    }

    @Transactional
    public TeamSettingDto findTeamByMemberId(String memberId) {
        Optional<Team> team = memberRepository.findById(memberId).map(Member::getTeam);
        if (team.isPresent()) {
            return TeamSettingDto.builder()
                    .memberId(memberId)
                    .teamId(team.get().getId())
                    .teamNm(team.get().getTeamNm())
                    .teamInfo(team.get().getTeamInfo())
                    .timeZone(team.get().getTimeZone())
                    .currency(team.get().getCurrency())
                    .language(team.get().getLanguage())
                    .build();
        } else {
            return null;
        }
    }

    public String findTeamIdByMemberId(String memberId) {
        return memberRepository.findById(memberId)
                .map(Member::getTeam)
                .map(Team::getId)
                .map(String::valueOf) // Long 타입의 ID를 String으로 변환
                .orElse(null);
    }

    public void deleteLogo(String Filegroup,String memberId) {
        fileStorageService.deleteFilesByGroupAndGroupId(Filegroup,memberId);
    }


}
