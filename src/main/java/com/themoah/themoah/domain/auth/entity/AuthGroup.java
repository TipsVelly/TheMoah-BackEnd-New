package com.themoah.themoah.domain.auth.entity;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder(toBuilder = true)
@Getter
public class AuthGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "auth_group_id")
    private Long id;

    private String groupNm;
    private String menuNm;

    private String cUser;
    private String uUser;

    @CreationTimestamp
    private LocalDateTime cdate;
    @UpdateTimestamp
    private LocalDateTime udate;
}
