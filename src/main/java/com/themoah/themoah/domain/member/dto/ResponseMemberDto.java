package com.themoah.themoah.domain.member.dto;

import com.themoah.themoah.domain.member.entity.Member;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ResponseMemberDto {
    private String  memberId;
    private String  memberName;
    private Long    teamId;
    private String  teamName;
    private String  address;
    private String  phoneNumber;

   
    @Builder
    public ResponseMemberDto(String memberId, String memberName, Long teamId, String teamName, String address,
            String phoneNumber) {
        this.memberId = memberId;
        this.memberName = memberName;
        this.teamId = teamId;
        this.teamName = teamName;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }



    public static ResponseMemberDto generate(Member member) {
        return ResponseMemberDto.builder()
                .memberId(member.getMemberId())
                .memberName(member.getMemberName())
                .phoneNumber(member.getPhoneNumber())
                .build();
    }
}
