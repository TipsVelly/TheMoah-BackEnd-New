package com.themoah.themoah.domain.auth.controller;

import com.themoah.themoah.domain.auth.dto.response.AuthMemberRespnseDTO;
import com.themoah.themoah.domain.auth.dto.request.AuthRequestDTO;
import com.themoah.themoah.domain.auth.dto.response.AuthResponseSelectDTO;
import com.themoah.themoah.domain.auth.dto.response.AuthResponseViewDTO;
import com.themoah.themoah.domain.auth.service.AuthMgmtService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Map;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@RestController
@RequiredArgsConstructor
@RequestMapping(value ="/api/v1/authMgmt", produces = APPLICATION_JSON_VALUE)
public class AuthMgmtController {
    private final AuthMgmtService authMgmtService;


    @GetMapping("/getAuthMemberList")
    @Operation(summary = "권한 멤버 목록 조회")
    public List<AuthMemberRespnseDTO> getAuthMemberList(Principal principal) {
        return authMgmtService.getAuthMemberList(principal);
    }

    @GetMapping("/getAuthList")
    @Operation(summary = "권한 목록 조회")
    public List<AuthResponseViewDTO> getAuthList(Principal principal) {
        return authMgmtService.getAuthList(principal);
    }

    @PostMapping("/save")
    @Operation(summary = "권한 저장 요청(사용자에 따른 기능은 추가로 작업 필요합니다)")
    public Map<String, Boolean> saveAuth(@RequestBody AuthRequestDTO authRequestDTO, Principal principal) {
        boolean rst = authMgmtService.saveAuth(authRequestDTO, principal);
        return Map.of("rst", rst);
    }

    @PostMapping("/update")
    @Operation(summary = "권한 수정 요청(사용자에 따른 기능은 추가로 작업 필요합니다)")
    public Map<String, Boolean> updateAuth(@RequestBody  AuthRequestDTO authRequestDTO) {
        boolean rst = authMgmtService.updateAuth(authRequestDTO);
        return Map.of("rst", rst);
    }

    @GetMapping("/select")
    @Operation(summary = "권한 조회")
    public AuthResponseSelectDTO update(@RequestParam("authId") Long authId) {
        return authMgmtService.getAuthSelectList(authId);
    }

    @GetMapping("/delete")
    @Operation(summary = "권한 삭제")
    public void AuthDelete(@RequestParam("authId") Long authId) {
        authMgmtService.deleteAuth(authId);
    }

    @GetMapping("/changeMemberAuth")
    @Operation(summary = "권한 멤버 변경")
    public void  changeMemberAuth(@RequestParam("authId") Long authId, Principal principal) {
        authMgmtService.changeMemberAuth(authId, principal);
    }
}
