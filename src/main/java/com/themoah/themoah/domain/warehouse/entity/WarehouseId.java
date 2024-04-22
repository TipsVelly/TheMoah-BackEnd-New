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

    @Column(name = "wh_code")
    private String warehouseCode;   //  창고코드

    @Column(name = "indust_code")
    private String industCode;      //  사업장코드
}
