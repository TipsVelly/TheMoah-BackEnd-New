package com.themoah.themoah.domain.auth.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.themoah.themoah.domain.auth.entity.Auth;
import lombok.*;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AuthResponseSelectDTO implements Serializable {
    @JsonProperty("auth_id")
    private Long authId;
    @JsonProperty("auth_nm")
    private String authNm;
    @JsonProperty("sub_auths")
    private List<SubAuth> subAuths;

    @Builder
    public AuthResponseSelectDTO(Long authId, String authNm, List<SubAuth> subAuths) {
        this.authId = authId;
        this.authNm = authNm;
        this.subAuths = subAuths;
    }

    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @Getter
    @Setter
    public static class SubAuth implements Serializable {
        @JsonProperty("sub_auth_id")
        private Long subAuthId;
        @JsonProperty("sub_auth_key")
        private String subAuthKey;
        @JsonProperty("sub_auth_nm")
        private String subAuthNm;

        @Builder
        public SubAuth(Long subAuthId, String subAuthKey, String subAuthNm) {
            this.subAuthId = subAuthId;
            this.subAuthKey = subAuthKey;
            this.subAuthNm = subAuthNm;
        }
    }

    public static AuthResponseSelectDTO convert(Auth auth) {
        AuthResponseSelectDTO authResponseSelectDTO = AuthResponseSelectDTO.builder()
                .authId(auth.getAuthId())
                .authNm(auth.getAuthNm())
                .subAuths(auth.getSubAuths().stream().map(subAuth -> {
                    return SubAuth.builder()
                            .subAuthId(subAuth.getSubAuthId())
                            .subAuthKey(subAuth.getSubAuthKey())
                            .subAuthNm(subAuth.getSubAuthNm())
                            .build();
                }).collect(Collectors.toList()))
                .build();

        return authResponseSelectDTO;
    }
}
