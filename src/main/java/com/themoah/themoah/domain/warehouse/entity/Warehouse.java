package com.themoah.themoah.domain.warehouse.entity;


import com.themoah.themoah.common.config.base.BaseTimeWithoutId;
import com.themoah.themoah.domain.industry.entity.Industry;
import com.themoah.themoah.domain.warehouse.dto.WarehouseDTO;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
public class Warehouse extends BaseTimeWithoutId {

    @EmbeddedId
    private WarehouseId warehouseId;

    @Column(name = "wh_name")
    private String warehouseName;   // 창고명

    private String matYn;           // 자재창고여부

    private String partYn;          // 구매입고창고여부

    private String prodYn;          // 제품창고여부
    
    private String vmiYn;           // 가상창고여부
    
    private String rtnYn;           // 출하창고여부

    private String saleYn;          // 매출창고여부

    private String subYn;           // 외주창고여부

    private String badYn;           // 불량창고여부

    private String inspYn;          // 검사대기여부

    private String minusYn;         // (-)재고허용

    private String useYn;           // 사용여부

    private String custCode;          // 거래처코드

    private String teamCode;          // 관리부서코드

    private Long dispSeq;        // 조회순서
    
    private String comment;         // 비고

    private String creater;         // 생성자

    private String updater;         // 수정자

    @MapsId("industCode")
    @JoinColumn(name = "indust_code")
    @ManyToOne(fetch = FetchType.LAZY)
    private Industry industry;

    public WarehouseDTO convertToWarehouseDTO() {
        return WarehouseDTO.builder()
                .warehouseCode(warehouseId.getWarehouseCode())
                .industCode(warehouseId.getIndustCode())
                .warehouseName(warehouseName)
                .matYn(matYn)
                .partYn(partYn)
                .prodYn(prodYn)
                .vmiYn(vmiYn)
                .rtnYn(rtnYn)
                .saleYn(saleYn)
                .subYn(subYn)
                .badYn(badYn)
                .inspYn(inspYn)
                .minusYn(minusYn)
                .useYn(useYn)
                .custCode(custCode)
                .teamCode(teamCode)
                .dispSeq(dispSeq)
                .creater(creater)
                .updater(updater)
                .comment(comment)
                .build();
    }
}
