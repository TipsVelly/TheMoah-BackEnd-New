package com.themoah.themoah.domain.auth.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.themoah.themoah.domain.auth.entity.Auth;
import lombok.*;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Builder
public class AuthResponseViewDTO {
    @JsonProperty(value = "auth_id")
    private Long authId;

    @JsonProperty(value = "auth_nm")
    private String authNm;

    @JsonProperty(value = "members")
    private List<String> members;

    public static AuthResponseViewDTO convert(Auth auth) {
        return AuthResponseViewDTO.builder()
                .authId(auth.getAuthId())
                .authNm(auth.getAuthNm())
                .build();
    }
}
