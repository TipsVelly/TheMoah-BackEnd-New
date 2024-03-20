package com.themoah.themoah.common.util.niceid.dto;

import lombok.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Builder
public class NiceIdResultData {
    private String responseNo;
    private String birthDate;
    private  String gender;
    private  String di;
    private  String receiveData;
    private  String mobileNo;
    private  String requestNo;
    private  String nationalInfo;
    private  String authType;
    private  String siteCode;
    private  String utf8Name;
    private  String encTime;
    private  String name;
    private  String resultCode;
}
