package com.themoah.themoah.domain.auth.controller;

import com.themoah.themoah.domain.auth.entity.AuthGroup;
import com.themoah.themoah.domain.auth.service.AuthMgmtService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/auth")
public class AuthMgmtController {
    private AuthMgmtService authMgmtService;

    @GetMapping("/list")
    public List<AuthGroup> authList(){
        return authMgmtService.authList();
    }
}
