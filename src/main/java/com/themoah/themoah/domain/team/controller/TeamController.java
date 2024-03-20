package com.themoah.themoah.domain.team.controller;

import com.themoah.themoah.domain.file.service.FileStorageService;
import com.themoah.themoah.domain.team.dto.TeamSettingDto;
import com.themoah.themoah.domain.team.service.TeamService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value ="/api/v1/team", produces = APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Tag(name = "TeamController", description = "팀 컨트롤러 API")
public class TeamController {
    private final TeamService teamService;
    private final FileStorageService fileStorageService;

    @Operation(summary = "Get Team Settings by Member ID", description = "멤버 ID로 팀 설정 정보를 가져옵니다.")
    @GetMapping("/{memberId}")
    public ResponseEntity<?> get(@PathVariable String memberId) {
        TeamSettingDto teamSettingDto = teamService.findTeamByMemberId(memberId);
        String s3logoUrl = fileStorageService.findFileUrlByGroupAndGroupId("TEAM", String.valueOf(teamSettingDto.getTeamId()));

        teamSettingDto.setLogoUrl(s3logoUrl);
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

//        String logoUrl = teamService.fileUpload(teamSettingDto);


        teamService.saveTeamSettings(teamSettingDto, memberId);
        return ResponseEntity.ok().build();
    }

    // TeamController.java 내에 추가
    @Operation(summary = "Upload team logo", description = "팀 로고를 업로드합니다.")
    @PostMapping("/uploadLogo")
    public ResponseEntity<?> uploadLogo(
            @RequestParam("logoFile") MultipartFile file,
            @RequestParam("memberId") String memberId
    ) {
        try {
            String fileGroup = "TEAM";
            // 멤버 ID로 팀 ID 조회
            String groupId = teamService.findTeamIdByMemberId(memberId);
            if (groupId == null) {
                // 팀 정보가 없는 경우 에러 처리
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Team not found for memberId: " + memberId);
            }
            // 파일 저장 로직 호출
            String savedFile = fileStorageService.fileUpload(file, fileGroup, groupId);

            // 저장된 파일에 대한 정보를 응답으로 반환
            return ResponseEntity.ok().body("File uploaded successfully: " + savedFile);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Could not upload the file: " + file.getOriginalFilename() + "!");
        }

    }

}