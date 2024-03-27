package com.themoah.themoah.domain.auth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.themoah.themoah.domain.auth.entity.Auth;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Builder
public class AuthResponseDTO {
    @JsonProperty(value = "auth_id")
    private Long authId;

    @JsonProperty(value = "auth_nm")
    private String authNm;

    public static AuthResponseDTO convert(Auth auth) {
        return AuthResponseDTO.builder()
                .authId(auth.getAuthId())
                .authNm(auth.getAuthNm())
                .build();
    }
}
