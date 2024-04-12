package com.themoah.themoah.domain.warehouse.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Embeddable
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@Builder
public class WarehouseId implements Serializable {

    @Column(name = "WH_CODE")
    private String warehouseCode;   //  창고코드

    @Column(name = "INDUST_CODE")
    private String industCode;      //  사업장코드
}
