package com.themoah.themoah.domain.auth.entity;

import com.themoah.themoah.domain.auth.dto.request.AuthRequestDTO;
import com.themoah.themoah.domain.auth.dto.request.SubAuthRequestDTO;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Entity
//@Table(
//    name = "sub_auth",
//        uniqueConstraints = {
//            @UniqueConstraint(
//                    name = "UniqueSubAuthKeyAndAuthId",
//                    columnNames = {
//                            "sub_auth_key",
//                            "auth_id"
//                    }
//            )
//        }
//)
@Getter
@Setter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class SubAuth {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sub_auth_id")
    private Long subAuthId;         // 서브 권한 아이디
    @Column(name = "sub_auth_key")
    private String subAuthKey;    // 서브 권한 이름
    @Column(name = "sub_auth_nm")
    private String subAuthNm;     // 서브 권한 이름

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "auth_id")
    private Auth auth;              // 권한

    public static List<SubAuth> toEntityList(AuthRequestDTO authRequestDTO, Auth auth) {
        List<SubAuthRequestDTO> subAuthRequestDTOList = authRequestDTO.getAuthRequestDTO();
        return subAuthRequestDTOList.stream()
                .map(subAuthRequestDTO -> SubAuth.builder()
                        .subAuthNm(subAuthRequestDTO.getSubAuthNm())
                        .subAuthKey(subAuthRequestDTO.getSubAuthKey())
                        .auth(auth)
                        .build())
                .collect(Collectors.toList());
    }

    /**
     * 연관관계 편의 메서드
     * @param auth
     */
    
    public void setAuth(Auth auth) {
        this.auth = auth;
        auth.getSubAuths().add(this);
    }

}
