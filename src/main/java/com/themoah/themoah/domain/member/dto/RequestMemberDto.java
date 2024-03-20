package com.themoah.themoah.domain.member.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RequestMemberDto {
    private String memberId;
    private String password;
    private String address;
    private String addressDetail;
    private String authToken;
   
    @Builder
    public RequestMemberDto(String userId, String password, String address, String authToken, String addressDetail) {
        this.memberId = userId;
        this.password = password;
        this.address = address;
        this.authToken = authToken;
        this.addressDetail = addressDetail;
    }
}
