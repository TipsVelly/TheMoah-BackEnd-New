package com.themoah.themoah.domain.verification.niceId.entity;

import com.themoah.themoah.common.config.base.BaseTimeWithoutId;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class NiceIdKey extends BaseTimeWithoutId {
    @Id
    private String tokenVersionId;  //토큰서버버전아이디
    private String key;             //암호화 키
}
