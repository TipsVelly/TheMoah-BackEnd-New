package com.themoah.themoah.common.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder(toBuilder = true)
public class TokenDto {
    private String  grantType;
    private String  accessToken;
    private String  refreshToken;
    private Long    accessTokenExpiresIn;
}
