package com.themoah.themoah.domain.team.entity;

import com.themoah.themoah.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

import static lombok.AccessLevel.PROTECTED;

@Entity
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor(access = PROTECTED)
@Builder(toBuilder = true)
@Getter
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "team_id")
    private Long id;
    @NonNull
    private String teamNm; // 팀명

    private String teamInfo; //팀정보

    private String timeZone; // 타임존

    private String currency; // 통화

    private String language; // 언어

    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Member> member = new ArrayList<>();

    // 변경 메서드
    public void updateTeamInfo(String teamNm, String teamInfo, String timeZone, String currency, String language) {
        this.teamNm = teamNm;
        this.teamInfo = teamInfo;
        this.timeZone = timeZone;
        this.currency = currency;
        this.language = language;
    }

}
