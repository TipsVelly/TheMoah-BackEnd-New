package com.themoah.themoah.domain.auth.entity;

import com.themoah.themoah.domain.auth.dto.AuthRequestDTO;
import com.themoah.themoah.domain.auth.dto.SubAuthRequestDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Entity
@Getter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class SubAuth {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sub_auth_id")
    private Long subAuthId;         // 서브 권한 아이디
    private String subAuthKey;    // 서브 권한 이름
    private String subAuthNm;     // 서브 권한 이름

    @ManyToOne
    @JoinColumn(name = "auth_id")
    private Auth auth;              // 권한

    public static List<SubAuth> toEntity(AuthRequestDTO authRequestDTO) {
        List<SubAuthRequestDTO> subAuthRequestDTOList = authRequestDTO.getAuthRequestDTO();
        return subAuthRequestDTOList.stream()
                .map(subAuthRequestDTO -> SubAuth.builder()
                        .subAuthNm(subAuthRequestDTO.getSubAuthNm())
                        .subAuthKey(subAuthRequestDTO.getSubAuthKey())
                        .build())
                .collect(Collectors.toList());
    }
}
