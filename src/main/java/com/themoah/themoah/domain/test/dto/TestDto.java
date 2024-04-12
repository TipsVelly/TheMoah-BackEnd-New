package com.themoah.themoah.domain.test.dto;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Builder
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TestDto {
    private long testId;
    private String industCode; //사업장코드
    private String poNo;//발주번호
    private LocalDate poDate;//발주일자
    private LocalDate divDate;//납기일자
    private String custCode;//거래처코드
    private String currency;//통화
    private String inWh;//입고창고코드
    private String comment;//비고
    private String statBc;//상태
    private int poAmount;//발주금액
    private List<TestDetailDto> testDetailDtos;

    public TestDto(TestDto testDto){
        this.industCode = testDto.getIndustCode();
        this.poNo = testDto.getPoNo();
        this.poDate = testDto.getPoDate();
        this.divDate = testDto.getDivDate();
        this.custCode = testDto.getCustCode();
        this.currency = testDto.getCurrency();
        this.inWh = testDto.getInWh();
        this.comment = testDto.getComment();
        this.statBc = testDto.getStatBc();
        this.poAmount = testDto.getPoAmount();
    }
}
