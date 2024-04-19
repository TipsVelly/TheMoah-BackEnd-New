package com.themoah.themoah.domain.customer.entity;

import com.themoah.themoah.domain.customer.dto.CustomerDto;
import com.themoah.themoah.domain.industry.entity.Industry;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class Customer {

    @EmbeddedId
    private CustomerId customerId;
    private String custName; //사업장명
    private String prtName; //인쇄용 거래처명
    private String custBc; //거래처구분
    private String custKd; //거래처형태
    private LocalDate fromDate; //거래개시일
    private LocalDate toDate; //거래종료일
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

    @MapsId("industCode")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "indust_code")
    private Industry industry;

    public void updateCustomer(CustomerDto customerDto) {
        this.customerId = CustomerId.builder()
                .industCode(customerDto.getIndustCode())
                .custCode(customerDto.getCustCode())
                .build();
        this.custName = customerDto.getCustName();
        this.coNo = customerDto.getCoNo();
        this.bankBc = customerDto.getBankBc();
        this.prtName = customerDto.getPrtName();
        this.custBc = customerDto.getCustBc();
        this.custKd = customerDto.getCustKd();
        this.acctNo = customerDto.getAcctNo();
        this.fromDate = customerDto.getFromDate();
        this.toDate = customerDto.getToDate();
        this.salYn = customerDto.getSalYn();
        this.purYn = customerDto.getPurYn();
        this.deBc = customerDto.getDeBc();
        this.dlBc = customerDto.getDlBc();
        this.taxBc = customerDto.getTaxBc();
        this.issBc = customerDto.getIssBc();
        this.salCust = customerDto.getSalCust();
        this.purCust = customerDto.getPurCust();
        this.pSetBc = customerDto.getPSetBc();
        this.dayBc = customerDto.getDayBc();
        this.payBc = customerDto.getPayBc();
        this.currency = customerDto.getCurrency();
        this.currency2 = customerDto.getCurrency2();
        this.zipCode = customerDto.getZipCode();
        this.add1 = customerDto.getAdd1();
        this.add2 = customerDto.getAdd2();
        this.addPrt = customerDto.getAddPrt();
        this.tel = customerDto.getTel();
        this.fax = customerDto.getFax();
        this.email = customerDto.getEmail();
        this.homepage = customerDto.getHomepage();
        this.esero = customerDto.getEsero();
        this.bizNo = customerDto.getBizNo();
        this.eTel = customerDto.getETel();
        this.bizType = customerDto.getBizType();
        this.bizKind = customerDto.getBizKind();
        this.ceoName = customerDto.getCeoName();
        this.items = customerDto.getItems();
        this.useYn = customerDto.getUseYn();
        this.uDate =  LocalDateTime.now();
        this.uUser = customerDto.getUUser();
    }

    public void setCustomerIndustry(Industry industry) {
        this.industry = industry;
    }

}
