package com.themoah.themoah.common.util.niceid.dto.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;


/**
 * 0000	인증 성공
 * 0001	"인증 불일치(통신사선택오류, 생년월일/성명/휴대폰번호
 * 불일치, 휴대폰일시정지, 선불폰가입자, SMS발송실패, 인
 * 증문자불일치 등)"
 * 0003	기타인증오류
 * 0010	인증번호 불일치(소켓)
 * 0012	요청정보오류(입력값오류)
 * 0013	암호화 시스템 오류
 * 0014	암호화 처리 오류
 * 0015	암호화 데이터 오류
 * 0016	복호화 처리 오류
 * 0017	복호화 데이터 오류
 * 0018	통신오류
 * 0019	데이터베이스 오류
 * 0020	유효하지않은 CP코드
 * 0021	중단된 CP코드
 * 0022	휴대전화본인확인 사용불가 CP코드
 * 0023	미등록 CP코드
 * 0031	유효한 인증이력 없음
 * 0035	기인증완료건(소켓)
 * 0040	본인확인차단고객(통신사)
 * 0041	인증문자발송차단고객(통신사)
 * 0050	NICE 명의보호서비스 이용고객차단
 * 0052	부정사용차단
 * 0070	간편인증앱 미설치
 * 0071	앱인증 미완료
 * 0072	간편인증 처리중 오류
 * 0073	간편인증앱 미설치(LG U+ Only)
 * 0074	간편인증앱 재설치필요
 * 0075	간편인증사용불가-스마트폰아님
 * 0076	간편인증앱 미설치
 * 0078	14세 미만 인증 오류
 * 0079	간편인증 시스템 오류
 * 9097	인증번호 3회 불일치
 * 그 외	기타오류
 */


@AllArgsConstructor
@Getter
public enum NiceIdStatusCode {
    AUTHENTICATION_SUCCESS(
            "0000",
            " Authentication Success",
            "인증 성공"
    ),

    AUTHENTICATION_MISMATCH(
            "0001",
            "Authentication Mismatch (Carrier Selection Error, DOB/Name/Mobile Number Mismatch, Mobile Suspension, Prepaid Subscriber, SMS Sending Failure, Authentication Text Mismatch, etc",
            "인증 불일치(통신사선택오류, 생년월일/성명/휴대폰번호 불일치, 휴대폰일시정지, 선불폰가입자, SMS발송실패, 인증문자불일치 등)"
    ),

    OTHER_AUTHENTICATION_ERROR(
            "0003",
            "Other Authentication Error",
            "기타인증오류"
    ),

    AUTHENTICATION_NUMBER_MISMATCH(
            "0010",
            "Authentication Number Mismatch (Socket)",
            "인증번호 불일치(소켓)"
    ),

    REQUEST_INFORMATION_ERROR(
            "0012",
            "Request Information Error (Input Value Error)",
            "요청정보오류(입력값오류)"
    ),

    ENCRYPTION_SYSTEM_ERROR(
            "0013",
            "Encryption System Error",
            "암호화 시스템 오류"
    ),

    ENCRYPTION_PROCESSING_ERROR(
            "0014",
            "Encryption Processing Error",
            "암호화 처리 오류"
    ),

    ENCRYPTION_DATA_ERROR(
            "0015",
            "Encryption Data Error",
            "암호화 데이터 오류"
    ),

    DECRYPTION_PROCESSING_ERROR(
            "0016",
            "Decryption Processing Error",
            "복호화 처리 오류"
    ),

    DECRYPTION_DATA_ERROR(
            "0017",
            "Decryption Data Error",
            "복호화 데이터 오류"
    ),

    COMMUNICATION_ERROR(
            "0018",
            "Communication Error",
            "통신오류"
    ),

    DATABASE_ERROR(
            "0019",
            "Database Error",
            "데이터베이스 오류"
    ),
    INVALID_CP_CODE(
            "0020",
            "Invalid CP Code",
            "유효하지않은 CP코드"
    ),

    SUSPENDED_CP_CODE(
            "0021",
            "Suspended CP Code",
            "중단된 CP코드"
    ),

    UNAVAILABLE_CP_CODE_FOR_MOBILE_AUTHENTICATION(
            "0022",
            "Unavailable CP Code for Mobile Authentication",
            "휴대전화본인확인 사용불가 CP코드"
    ),

    UNREGISTERED_CP_CODE(
            "0023",
            "Unregistered CP Code",
            "미등록 CP코드"
    ),

    NO_VALID_AUTHENTICATION_HISTORY(
            "0031",
            "No Valid Authentication History",
            "유효한 인증이력 없음"
    ),

    PRE_AUTHENTICATED_CASE(
            "0035",
            "Pre-authenticated Case (Socket)",
            "기인증완료건(소켓)"
    ),

    CUSTOMER_BLOCKING_IDENTITY_VERIFICATION(
            "0040",
            "Customer Blocking Identity Verification (Carrier)",
            "본인확인차단고객(통신사)"
    ),

    CUSTOMER_BLOCKING_AUTHENTICATION_SMS_SENDING(
            "0041",
            "Customer Blocking Authentication SMS Sending (Carrier)",
            "인증문자발송차단고객(통신사)"
    ),

    NICE_NAME_PROTECTION_SERVICE_CUSTOMER_BLOCKING(
            "0050",
            "NICE Name Protection Service Customer Blocking",
            "NICE 명의보호서비스 이용고객차단"
    ),

    ILLEGAL_USAGE_BLOCKING(
            "0052",
            "Illegal Usage Blocking",
            "부정사용차단"
    ),


    EASY_AUTHENTICATION_APP_NOT_INSTALLED_70(
            "0070",
            "Easy Authentication App Not Installed_70Error",
            "간편인증앱 미설치_70"
    ),

    APP_AUTHENTICATION_NOT_COMPLETED(
            "0071",
            "App Authentication Not Completed",
            "앱인증 미완료"
    ),

    EASY_AUTHENTICATION_PROCESSING_ERROR(
            "0072",
            "Easy Authentication Processing Error",
            "간편인증 처리중 오류"
    ),

    EASY_AUTHENTICATION_APP_NOT_INSTALLED_LG_UPLUS_ONLY(
            "0073",
            "Easy Authentication App Not Installed (LG U+ Only)",
            "간편인증앱 미설치(LG U+ Only)"
    ),

    EASY_AUTHENTICATION_APP_REINSTALLATION_REQUIRED(
            "0074",
            "Easy Authentication App Reinstallation Required",
            "간편인증앱 재설치필요"
    ),

    EASY_AUTHENTICATION_UNAVAILABLE_NOT_A_SMARTPHONE(
            "0075",
            "Easy Authentication Unavailable - Not a Smartphone",
            "간편인증사용불가-스마트폰아님"
    ),

    EASY_AUTHENTICATION_APP_NOT_INSTALLED_76(
            "0076",
            "Easy Authentication App Not Installed_76Error",
            "간편인증앱 미설치"
    ),

    UNDER_14_AGE_AUTHENTICATION_ERROR(
            "0078",
            "Under 14 Age Authentication Error",
            "14세 미만 인증 오류"
    ),

    EASY_AUTHENTICATION_SYSTEM_ERROR(
            "0079",
            "Easy Authentication System Error",
            "간편인증 시스템 오류"
    ),

    AUTHENTICATION_NUMBER_MISMATCHED_3_TIMES(
            "9097",
            "Authentication Number Mismatched 3 Times",
            "인증번호 3회 불일치"
    ),

    OTHER_ERRORS(
            "9999",
            "Others Other Errors",
            "기타오류"
    );

    private final String code;
    private final String message;
    private final String korMessage;

}
