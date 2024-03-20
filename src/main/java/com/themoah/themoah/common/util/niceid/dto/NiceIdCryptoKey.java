package com.themoah.themoah.common.util.niceid.dto;

import lombok.*;

@Builder
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class NiceIdCryptoKey {
    private String key; //  대칭키
    private String iv;  //  초기화 벡터
    private String hmacKey; // 암호화값 위변조 체크용 키
    private String siteCode; // 사이트코드
    private String tokenVersionId; //서버 토큰 버전
}
