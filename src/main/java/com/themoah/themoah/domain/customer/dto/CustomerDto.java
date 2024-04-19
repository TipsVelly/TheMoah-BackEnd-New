package com.themoah.themoah.domain.customer.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CustomerDto {
    @JsonProperty("custCode")
    private String custCode;
    @JsonProperty("industCode")
    private String industCode;
    @JsonProperty("custName")
    private String custName; //사업장명
    @JsonProperty("prtName")
    private String prtName; //인쇄용 거래처명
    @JsonProperty("custBc")
    private String custBc; //거래처구분
    @JsonProperty("custKd")
    private String custKd; //거래처형태
    @JsonProperty("fromDate")
    private LocalDate fromDate; //거래개시일
    @JsonProperty("toDate")
    private LocalDate toDate; //거래종료일
    @JsonProperty("salYn")
    private String salYn; //거래여부
    @JsonProperty("purYn")
    private String purYn; //매입여부
    @JsonProperty("deBc")
    private String deBc; //매출지역구분
    @JsonProperty("dlBc")
    private String dlBc; //매입지역구분
    @JsonProperty("taxBc")
    private String taxBc; //매출부가세구분
    @JsonProperty("issBc")
    private String issBc; //전자계산서발행구분
    @JsonProperty("salCust")
    private String salCust; //매출정산거래처코드
    @JsonProperty("purCust")
    private String purCust; //매입정산거래처코드
    @JsonProperty("pSetBc")
    private String pSetBc; //매입결재조건
    @JsonProperty("dayBc")
    private String dayBc; //정기지급일
    @JsonProperty("payBc")
    private String payBc; //결제지급구분
    @JsonProperty("currency")
    private String currency; //결재통화코드
    @JsonProperty("currency2")
    private String currency2; //통화코드
    @JsonProperty("zipCode")
    private String zipCode; //우편번호
    @JsonProperty("add1")
    private String add1; //주소1
    @JsonProperty("add2")
    private String add2; //
    @JsonProperty("addPrt")
    private String addPrt; //인쇄용주소
    @JsonProperty("tel")
    private String tel; //대표전화
    @JsonProperty("fax")
    private String fax; //대표FAX
    @JsonProperty("email")
    private String email; //이메일주소
    @JsonProperty("homepage")
    private String homepage; //홈페이지주소
    @JsonProperty("esero")
    private String esero; //전자계산서담당
    @JsonProperty("bizNo")
    private String bizNo; //사업자등록번호
    @JsonProperty("eTel")
    private String eTel; //계산서담당전화
    @JsonProperty("bizType")
    private String bizType; //업태(인쇄용)
    @JsonProperty("bizKind")
    private String bizKind; //업종(인쇄용)
    @JsonProperty("ceoName")
    private String ceoName; //대표자명
    @JsonProperty("items")
    private String items; //거래품목
    @JsonProperty("acctYn")
    private String acctYn; //계좌개설여부
    @JsonProperty("bankBc")
    private String bankBc; //은행코드
    @JsonProperty("acctName")
    private String acctName; //예금주
    @JsonProperty("acctNo")
    private String acctNo; //주거래계좌번호
    @JsonProperty("coNo")
    private String coNo; //법인등록번호
    @JsonProperty("useYn")
    private String useYn; //거래여부
    @JsonProperty("inspBc")
    private String inspBc; //검사구분
    @JsonProperty("empNo")
    private String empNo; //담당자(우리사업장측)
    @JsonProperty("comment")
    private String comment; //비고
    private LocalDateTime cDate; //생성일
    @JsonProperty("cUser")
    private String cUser; //생성자
    private LocalDateTime uDate; //수정일
    @JsonProperty("uUser")
    private String uUser; //수정자
    @JsonProperty("salPur")
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
