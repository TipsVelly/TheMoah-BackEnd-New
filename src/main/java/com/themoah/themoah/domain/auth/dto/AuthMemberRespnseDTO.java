package com.themoah.themoah.domain.auth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.themoah.themoah.domain.auth.entity.Auth;
import com.themoah.themoah.domain.member.entity.Member;
import lombok.*;

@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class AuthMemberRespnseDTO {

    @JsonProperty(value = "member_id")
    private String memberId;
    
    @JsonProperty(value = "member_nm")
    private String memberNm;

    @JsonProperty(value = "auth_id")
    private Long authId;

    @JsonProperty(value = "auth_nm")
    private String authNm;


    public static AuthMemberRespnseDTO convert(Member member) {
        AuthMemberRespnseDTO authMemberRespnseDTO = null;
        if(member.getAuth() ==  null) {
            authMemberRespnseDTO = AuthMemberRespnseDTO.builder()
                    .memberId(member.getMemberId())
                    .memberNm(member.getMemberName())
                    .build();
        } else {
            authMemberRespnseDTO = AuthMemberRespnseDTO.builder()
                    .memberId(member.getMemberId())
                    .memberNm(member.getMemberName())
                    .authId(member.getAuth().getAuthId())
                    .authNm(member.getAuth().getAuthNm())
                    .build();
        }

        return authMemberRespnseDTO;
    }

    public static AuthMemberRespnseDTO convert(Auth auth) {
        return AuthMemberRespnseDTO.builder()
                .authId(auth.getAuthId())
                .authNm(auth.getAuthNm())
                .build();
    }
}
