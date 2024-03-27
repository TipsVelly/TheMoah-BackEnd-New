package com.themoah.themoah.domain.auth.entity;


import com.themoah.themoah.domain.auth.dto.AuthRequestDTO;
import com.themoah.themoah.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Auth {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "auth_id")
    private Long    authId;     // 권한 아이디

    private String  authNm;     // 권한 이름

    @OneToMany(mappedBy = "auth", fetch = FetchType.LAZY)
    private List<SubAuth> subAuths;

    @OneToMany(mappedBy = "auth", fetch = FetchType.LAZY)
    private List<Member> members;

    @Builder
    public Auth(Long authId, String authNm) {
        this.authId = authId;
        this.authNm = authNm;
    }

    public static Auth toEntity(AuthRequestDTO authRequestDTO) {
        return Auth.builder()
                .authNm(authRequestDTO.getAuthNm())
                .build();
    }

    /**
     * 연관관계 편의 메서드
     */

    public void addSubAuth(SubAuth subAuth) {
        this.subAuths.add(subAuth);
        subAuth.setAuth(this);
    }
}
