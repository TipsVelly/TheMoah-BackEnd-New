package com.themoah.themoah.domain.warehouse.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.themoah.themoah.domain.industry.entity.Industry;
import com.themoah.themoah.domain.warehouse.entity.Warehouse;
import com.themoah.themoah.domain.warehouse.entity.WarehouseId;
import lombok.*;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@Builder
public class WarehouseDTO implements Serializable {

    @JsonProperty("wh_code")
    private String warehouseCode;   //  창고코드

    @JsonProperty("indust_code")
    private String industCode;      //  사업장코드

    @JsonProperty("wh_name")
    private String warehouseName;   // 창고명

    @JsonProperty("mat_yn")
    private String matYn;           // 자재창고여부

    @JsonProperty("part_yn")
    private String partYn;          // 구매입고창고여부

    @JsonProperty("prod_yn")
    private String prodYn;          // 제품창고여부

    @JsonProperty("vmi_yn")
    private String vmiYn;           // 가상창고여부

    @JsonProperty("rtn_yn")
    private String rtnYn;           // 출하창고여부

    @JsonProperty("sale_yn")
    private String saleYn;          // 매출창고여부

    @JsonProperty("sub_yn")
    private String subYn;           // 외주창고여부

    @JsonProperty("bad_yn")
    private String badYn;           // 불량창고여부

    @JsonProperty("insp_yn")
    private String inspYn;          // 검사대기여부

    @JsonProperty("minus_yn")
    private String minusYn;         // (-)재고허용

    @JsonProperty("use_yn")
    private String useYn;           // 사용여부

    @JsonProperty("cust_code")
    private String custCode;          // 거래처코드

    @JsonProperty("team_code")
    private String teamCode;          // 관리부서코드

    @JsonProperty("disp_seq")
    private Long dispSeq;        // 조회순서

    @JsonProperty("creater")
    private String creater;         // 생성자

    @JsonProperty("updater")
    private String updater;         // 수정자

    @JsonProperty("comment")
    private String comment;         // 비고


    public Warehouse convertToWarehouse() {
        return Warehouse.builder()
                .warehouseId(WarehouseId.builder()
                        .warehouseCode(warehouseCode)
                        .industCode(industCode)
                        .build())
                .warehouseName(warehouseName)
                .matYn(convertToYn(matYn))
                .partYn(convertToYn(partYn))
                .prodYn(convertToYn(prodYn))
                .vmiYn(convertToYn(vmiYn))
                .rtnYn(convertToYn(rtnYn))
                .saleYn(convertToYn(saleYn))
                .subYn(convertToYn(subYn))
                .badYn(convertToYn(badYn))
                .inspYn(convertToYn(inspYn))
                .minusYn(convertToYn(minusYn))
                .useYn(convertToYn(useYn))
                .custCode(custCode)
                .teamCode(teamCode)
                .dispSeq(dispSeq)
                .creater(creater)
                .updater(updater)
                .comment(comment)
                .build();
    }

    public Warehouse convertToWarehouse(Long count) {
        return Warehouse.builder()
                .warehouseId(WarehouseId.builder()
                        .warehouseCode(StringUtils.hasText(warehouseCode) ? warehouseCode : "default_wh_0" + (++count))
                        .industCode(industCode)
                        .build())
                .warehouseName(warehouseName)
                .matYn(convertToYn(matYn))
                .partYn(convertToYn(partYn))
                .prodYn(convertToYn(prodYn))
                .vmiYn(convertToYn(vmiYn))
                .rtnYn(convertToYn(rtnYn))
                .saleYn(convertToYn(saleYn))
                .subYn(convertToYn(subYn))
                .badYn(convertToYn(badYn))
                .inspYn(convertToYn(inspYn))
                .minusYn(convertToYn(minusYn))
                .useYn(convertToYn(useYn))
                .custCode(StringUtils.hasText(custCode) ? custCode : null)
                .teamCode(StringUtils.hasText(teamCode) ? teamCode : null)
                .dispSeq(++count)
                .creater(creater)
                .updater(updater)
                .comment(comment)
                .industry(Industry.builder()
                        .industCode(industCode)
                        .build())
                .build();
    }

    private String convertToYn(String yn) {
        List<String> convertYnList = List.of("Y", "True", "true");
        return StringUtils.hasText(yn) &&  convertYnList.contains(yn) ? "Y" : "N";
    }
}
