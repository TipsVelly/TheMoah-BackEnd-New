package com.themoah.themoah.common.util.niceid.dto;

import lombok.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
@ToString
public class NiceIdEncData {
        private String tokenVersionId;
        private String encData;
        private String integrityValue;

}
