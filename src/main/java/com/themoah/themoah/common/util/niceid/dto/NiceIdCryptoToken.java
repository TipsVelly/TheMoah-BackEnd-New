package com.themoah.themoah.common.util.niceid.dto;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class NiceIdCryptoToken {
    private String cryptoToken;
    private String siteCode;
    private String tokenVersionId;
}
