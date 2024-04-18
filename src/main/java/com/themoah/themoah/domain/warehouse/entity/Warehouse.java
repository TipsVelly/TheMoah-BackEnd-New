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

    @Column(name = "WH_NAME")
    private String warehouseName;   // 창고명

    @Column(name = "MAT_YN")
    private String matYn;           // 자재창고여부

    @Column(name = "PART_YN")
    private String partYn;          // 구매입고창고여부

    @Column(name = "PROD_YN")
    private String prodYn;          // 제품창고여부
    
    @Column(name = "VMI_YN")
    private String vmiYn;           // 가상창고여부
    
    @Column(name = "RTN_YN")
    private String rtnYn;           // 출하창고여부

    @Column(name = "SALE_YN")
    private String saleYn;          // 매출창고여부

    @Column(name = "SUB_YN")
    private String subYn;           // 외주창고여부

    @Column(name = "BAD_YN")
    private String badYn;           // 불량창고여부

    @Column(name = "INSP_YN")
    private String IsnpYn;          // 검사대기여부

    @Column(name = "MINUS_YN")
    private String minusYn;         // (-)재고허용

    @Column(name = "USE_YN")
    private String useYn;           // 사용여부

    @Column(name = "CUST_ID")
    private String custId;          // 거래처코드

    @Column(name = "DEPT_ID")
    private String deptId;          // 관리부서코드

    @Column(name = "DISP_SEQ")
    private Long dispSeq;        // 조회순서
    
    @Column(name = "COMMENT")
    private String comment;         // 비고

    @Column(name = "CREATER")
    private String creater;         // 생성자

    @Column(name = "UPDATER")
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
                .IsnpYn(IsnpYn)
                .minusYn(minusYn)
                .useYn(useYn)
                .custId(custId)
                .deptId(deptId)
                .dispSeq(dispSeq)
                .creater(creater)
                .updater(updater)
                .comment(comment)
                .build();
    }
}
