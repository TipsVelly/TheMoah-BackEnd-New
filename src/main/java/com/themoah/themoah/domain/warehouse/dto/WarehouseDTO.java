package com.themoah.themoah.domain.warehouse.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.themoah.themoah.domain.warehouse.entity.Warehouse;
import com.themoah.themoah.domain.warehouse.entity.WarehouseId;
import lombok.*;
import org.springframework.util.StringUtils;

import java.io.Serializable;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@Builder
public class WarehouseDTO implements Serializable {

    @JsonProperty("WH_CODE")
    private String warehouseCode;   //  창고코드

    @JsonProperty("INDUST_CODE")
    private String industCode;      //  사업장코드

    @JsonProperty("WH_NAME")
    private String warehouseName;   // 창고명

    @JsonProperty("MAT_YN")
    private String matYn;           // 자재창고여부

    @JsonProperty("PART_YN")
    private String partYn;          // 구매입고창고여부

    @JsonProperty("PROD_YN")
    private String prodYn;          // 제품창고여부

    @JsonProperty("VMI_YN")
    private String vmiYn;           // 가상창고여부

    @JsonProperty("RTN_YN")
    private String rtnYn;           // 출하창고여부

    @JsonProperty("SALE_YN")
    private String saleYn;          // 매출창고여부

    @JsonProperty("SUB_YN")
    private String subYn;           // 외주창고여부

    @JsonProperty("BAD_YN")
    private String badYn;           // 불량창고여부

    @JsonProperty("INSP_YN")
    private String IsnpYn;          // 검사대기여부

    @JsonProperty("MINUS_YN")
    private String minusYn;         // (-)재고허용

    @JsonProperty("USE_YN")
    private String useYn;           // 사용여부

    @JsonProperty("CUST_ID")
    private String custId;          // 거래처코드

    @JsonProperty("DEPT_ID")
    private String deptId;          // 관리부서코드

    @JsonProperty("DISP_SEQ")
    private Long dispSeq;        // 조회순서

    @JsonProperty("CREATER")
    private String creater;         // 생성자

    @JsonProperty("UPDATER")
    private String updater;         // 수정자

    @JsonProperty("COMMENT")
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
                .IsnpYn(convertToYn(IsnpYn))
                .minusYn(convertToYn(minusYn))
                .useYn(convertToYn(useYn))
                .custId(custId)
                .deptId(deptId)
                .dispSeq(dispSeq)
                .creater(creater)
                .updater(updater)
                .comment(comment)
                .build();
    }

    public Warehouse convertToWarehouse(Long count) {
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
                .IsnpYn(convertToYn(IsnpYn))
                .minusYn(convertToYn(minusYn))
                .useYn(convertToYn(useYn))
                .custId(custId)
                .deptId(deptId)
                .dispSeq(count)
                .creater(creater)
                .updater(updater)
                .comment(comment)
                .build();
    }

    private String convertToYn(String yn) {
        return StringUtils.hasText(yn) && "Y".equals(yn) ? "Y" : "N";
    }
}
