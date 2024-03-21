package com.themoah.themoah.domain.auth.controller;

import com.themoah.themoah.domain.auth.dto.AuthRequestDTO;
import com.themoah.themoah.domain.auth.service.AuthMgmtService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@RestController
@RequiredArgsConstructor
@RequestMapping(value ="/api/v1/authMgmt", produces = APPLICATION_JSON_VALUE)
public class AuthMgmtController {
    private final AuthMgmtService authMgmtService;

    @PostMapping
    @Operation(summary = "권한 저장 요청(사용자에 따른 기능은 추가로 작업 필요합니다)")
    public ResponseEntity<?> saveAuth(@RequestBody AuthRequestDTO authRequestDTO) {
        authMgmtService.saveAuth(authRequestDTO);
        return ResponseEntity.ok().build();
    }
}
