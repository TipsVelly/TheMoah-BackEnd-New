package com.themoah.themoah.domain.industry.entity;

import com.themoah.themoah.domain.customer.entity.Customer;
import com.themoah.themoah.domain.industry.dto.IndustryDTO;
import com.themoah.themoah.domain.warehouse.entity.Warehouse;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Industry {
    @Id
    private String industCode; // 사업장코드
    @NotNull
    private String industName; // 사업장명
    private String bizCode; // 사업자등록번호
    private String ownerNm; // 대표자명
    private String industAddr; // 사업장주소
    private String categoryNm; // 업종명
    private String bizCondNm; // 업태
    private String currency; //기준통화코드
    private String useYn; // 사용여부

    @OneToMany(mappedBy = "industry",cascade = CascadeType.ALL)
    @Builder.Default
    private List<Customer> customerList = new ArrayList<>();

    @OneToMany(mappedBy = "industry",cascade = CascadeType.ALL)
    @Builder.Default
    private List<Warehouse> warehouseList = new ArrayList<>();


    public IndustryDTO toDTO() {
        return IndustryDTO.builder()
                .industCode(industCode)
                .industName(industName)
                .bizCode(bizCode)
                .ownerNm(ownerNm)
                .industAddr(industAddr)
                .categoryNm(categoryNm)
                .bizCondNm(bizCondNm)
                .currency(currency)
                .useYn(useYn)
                .build();
    }
}
