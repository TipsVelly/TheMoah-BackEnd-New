package com.themoah.themoah.domain.industry.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
public class IndustryDTO implements Serializable {
    @JsonProperty("indust_code")
    private String industCode; // 사업장코드

    @JsonProperty("indust_name")
    private String industName; // 사업장명

    @JsonProperty("biz_code")
    private String bizCode; // 사업자등록번호

    @JsonProperty("owner_nm")
    private String ownerNm; // 대표자명

    @JsonProperty("indust_addr")
    private String industAddr; // 사업장주소

    @JsonProperty("category_nm")
    private String categoryNm; // 업종

    @JsonProperty("biz_cond_nm")
    private String bizCondNm; // 업태

    @JsonProperty("currency")
    private String currency; // 통화

    @JsonProperty("use_yn")
    private String useYn; // 사용여부
}
