package com.themoah.themoah.domain.customer.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CustomerDto {
    private String custCode;
    private String industCode;
    private String custName; //사업장명
    private String prtName; //인쇄용 거래처명
    private String custBc; //거래처구분
    private String custKd; //거래처형태
    private LocalDateTime fromDate; //거래개시일
    private LocalDateTime toDate; //거래종료일
    private String salYn; //거래여부
    private String purYn; //매입여부
    private String deBc; //매출지역구분
    private String dlBc; //매입지역구분
    private String taxBc; //매출부가세구분
    private String issBc; //전자계산서발행구분
    private String salCust; //매출정산거래처코드
    private String purCust; //매입정산거래처코드
    private String pSetBc; //매입결재조건
    private String dayBc; //정기지급일
    private String payBc; //결제지급구분
    private String currency; //결재통화코드
    private String currency2; //통화코드
    private String zipCode; //우편번호
    private String add1; //주소1
    private String add2; //주소2
    private String addPrt; //인쇄용주소
    private String tel; //대표전화
    private String fax; //대표FAX
    private String email; //이메일주소
    private String homepage; //홈페이지주소
    private String esero; //전자계산서담당
    private String bizNo; //사업자등록번호
    private String eTel; //계산서담당전화
    private String bizType; //업태(인쇄용)
    private String bizKind; //업종(인쇄용)
    private String ceoName; //대표자명
    private String items; //거래품목
    private String acctYn; //계좌개설여부
    private String bankBc; //은행코드
    private String acctName; //예금주
    private String acctNo; //주거래계좌번호
    private String coNo; //법인등록번호
    private String useYn; //거래여부
    private String inspBc; //검사구분
    private String empNo; //담당자(우리사업장측)
    private String comment; //비고
    private LocalDateTime cDate; //생성일
    private String cUser; //생성자
    private LocalDateTime uDate; //수정일
    private String uUser; //수정자
    private String salPur; // 매입/매출 구분 표기

    public CustomerDto(String custBc, String custKd, String erero, String tel, String empNo, String custName, String salYn, String purYn) {
        this.custBc = custBc;
        this.custKd = custKd;
        this.esero = erero;
        this.tel = tel;
        this.empNo = empNo;
        this.custName = custName;
        this.salYn = salYn;
        this.purYn = purYn;
    }
}
