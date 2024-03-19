package com.themoah.themoah.domain.team.controller;

import com.themoah.themoah.common.config.annotations.LoginMemberId;
import com.themoah.themoah.domain.member.entity.Member;
import com.themoah.themoah.domain.team.service.TeamService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.themoah.themoah.domain.team.dto.TeamSettingDto;

import java.security.Principal;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value ="/api/v1/team", produces = APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Tag(name = "TeamController", description = "팀 컨트롤러 API")
public class TeamController {
    private final TeamService teamService;

    @Operation(summary = "Get Team Settings by Member ID", description = "멤버 ID로 팀 설정 정보를 가져옵니다.")
    @GetMapping("/{memberId}")
    public ResponseEntity<?> get(@PathVariable String memberId) {
        TeamSettingDto teamSettingDto = teamService.findTeamByMemberId(memberId);
        if (teamSettingDto != null) {
            return ResponseEntity.ok(teamSettingDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @Operation(summary = "Set Team Settings by Member ID", description = "멤버 ID로 팀 설정 정보를 수정합니다.")
    @PostMapping("/teamSetting")
    public ResponseEntity<?> saveTeamInfo(@RequestBody TeamSettingDto teamSettingDto) {
        String memberId = teamSettingDto.getMemberId();
        System.out.println("memberId " + memberId);
        teamService.saveTeamSettings(teamSettingDto, memberId);
        return ResponseEntity.ok().build();
    }
}
