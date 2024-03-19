package com.themoah.themoah.domain.team.dto;

import com.themoah.themoah.domain.team.entity.Team;
import lombok.*;
import org.springframework.lang.NonNull;

import static lombok.AccessLevel.PROTECTED;

@Getter
@Setter
@Builder
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor(access = PROTECTED)
public class TeamSettingDto {
    private String teamNm;
    private String teamInfo;
    private String timeZone;
    private String currency;
    private String language;


    private String memberId;


    public TeamSettingDto(Team team, String memberId){
        this.teamNm = team.getTeamNm();
        this.teamInfo = team.getTeamInfo();
        this.timeZone = team.getTimeZone();
        this.currency = team.getCurrency();
        this.language = team.getLanguage();
        this.memberId = memberId;
    }

}
