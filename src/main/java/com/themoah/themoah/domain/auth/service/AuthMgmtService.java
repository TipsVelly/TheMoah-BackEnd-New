package com.themoah.themoah.domain.auth.service;

import com.themoah.themoah.domain.auth.dto.AuthRequestDTO;
import com.themoah.themoah.domain.auth.dto.SubAuthRequestDTO;
import com.themoah.themoah.domain.auth.entity.Auth;
import com.themoah.themoah.domain.auth.entity.SubAuth;
import com.themoah.themoah.domain.auth.repository.AuthMgmtRepository;
import com.themoah.themoah.domain.auth.repository.SubAuthMgmtRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthMgmtService {
    private final AuthMgmtRepository authMgmtRepository;
    private final SubAuthMgmtRepository subAuthMgmtRepository;

    public void saveAuth(AuthRequestDTO authRequestDTO) {
        Auth auth = Auth.toEntity(authRequestDTO);
        authMgmtRepository.save(auth);

        List<SubAuthRequestDTO> subAuthDTOList = authRequestDTO.getAuthRequestDTO(); // SubAuth DTO 목록을 가져옴
        List<SubAuth> subAuthList = new ArrayList<>();

        for(SubAuthRequestDTO subAuthRequestDTO : subAuthDTOList){
            SubAuth subAuth = SubAuth.builder()
                    .subAuthNm(subAuthRequestDTO.getSubAuthNm())
                    .subAuthKey(subAuthRequestDTO.getSubAuthKey())
                    .auth(auth)
                    .build();
            subAuthList.add(subAuth);
        }
        subAuthMgmtRepository.saveAll(subAuthList);
    }
}
