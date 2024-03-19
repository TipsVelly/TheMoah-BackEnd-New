package com.themoah.themoah.domain.auth.service;

import com.themoah.themoah.domain.auth.entity.AuthGroup;
import com.themoah.themoah.domain.auth.repository.AuthMgmtRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthMgmtService {
    private AuthMgmtRepository authMgmtRepository;

    public List<AuthGroup> authList(){
        return authMgmtRepository.findAll();
    }
}
