package com.themoah.themoah.common.util.niceid.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.util.StringUtils;

import java.io.Serializable;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Builder
public class NiceIdResultData implements Serializable {

    // 결과코드
    @JsonProperty("resultcode")
    private String resultCode;

    // 요청 고유 번호(회원사에서 전달보낸 값)
    @JsonProperty("requestno")
    private String requestNo;

    // 암호화 일시(YYYYMMDDHH24MISS)
    @JsonProperty("enctime")
    private String encTime;

    // 사이트코드
    @JsonProperty("sitecode")
    private String siteCode;

    // 응답고유번호
    @JsonProperty("responseno")
    private String responseNo;

    // 인증수단
    @JsonProperty("authtype")
    private String authType;

    // 이름
    @JsonProperty("name")
    private String name;

    // UTF8로 URLEncoding된 이름 값
    @JsonProperty("utf8_name")
    private String utf8Name;

    // 생년월일 8자리
    @JsonProperty("birthdate")
    private String birthDate;

    // 성별
    @JsonProperty("gender")
    private String gender;

    // 내외국인
    @JsonProperty("nationalinfo")
    private String nationalInfo;

    // 이통사 구분(휴대폰 인증 시)
    @JsonProperty("mobileco")
    private String mobileCo;

    // 휴대폰 번호(휴대폰 인증 시)
    @JsonProperty("mobileno")
    private String mobileNo;

    // 개인 식별 코드(CI)
    @JsonProperty("ci")
    private String ci;

    // 개인 식별 코드(DI)
    @JsonProperty("di")
    private String di;

    // 사업자번호(법인인증서 인증시)
    @JsonProperty("businessno")
    private String businessNo;

    // 요청 시 전달 받은 RECEIVEDATA
    @JsonProperty("receivedata")
    private String receiveData;


    public String readGender() {
        if(StringUtils.hasText(gender)){
            if("0".equals(gender)) {
                return "여";
            } else {
                return "남";
            }
        }
        return "";
    }

    public String readNationalInfo() {
        if(StringUtils.hasText(nationalInfo)){
            if("0".equals(nationalInfo)) {
                return "내국인";
            } else {
                return "외국인";
            }
        }
        return "";
    }

    public String readAuthType() {
        switch (authType) {
            case "M":
                return "휴대폰인증";
            case "C":
                return "카드본인확인";
            case "X":
                return "공동인증서";
            case "F":
                return "금융인증서";
            case "S":
                return "PASS인증서";
        }
        return "";
    }

    public String readMobileCo() {
        switch (mobileCo) {
            case "1":
                return "SK텔레콤";
            case "2":
                return "KTF";
            case "3":
                return "LGU+";
            case "5":
                return "SK텔레콤 알뜰폰";
            case "6":
                return "KT 알뜰폰";
            case "7":
                return "LGU+ 알뜰폰";
        }
        return "";
    }
}
