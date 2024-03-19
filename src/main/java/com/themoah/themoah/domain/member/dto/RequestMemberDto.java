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
    private String memberName;
    private String nickname;
    private String password;
    private String address;
    private String phoneNumber;
   
    @Builder
    public RequestMemberDto(String userId, String username, String nickname, String password, String address,
            String phoneNumber) {
        this.memberId = userId;
        this.memberName = username;
        this.nickname = nickname;
        this.password = password;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }
}
