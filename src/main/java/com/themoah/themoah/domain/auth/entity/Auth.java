package com.themoah.themoah.domain.auth.entity;


import com.themoah.themoah.domain.auth.dto.AuthRequestDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Getter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Auth {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "auth_id")
    private Long    authId;     // 권한 아이디

    private String  authNm;     // 권한 이름

    @OneToMany(mappedBy = "auth", cascade = CascadeType.ALL)
    private List<SubAuth>  subAuth;

    public static Auth toEntity(AuthRequestDTO authRequestDTO) {
        return Auth.builder()
                .authNm(authRequestDTO.getAuthNm())
                .build();
    }
}
