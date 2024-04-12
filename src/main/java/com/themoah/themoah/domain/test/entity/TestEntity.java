package com.themoah.themoah.domain.test.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class TestEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long testId;

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
    @OneToMany(mappedBy = "testEntity" , cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TestDetailEntity> testDetailEntity;





}
