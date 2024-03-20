package com.themoah.themoah.domain.member.entity;

import com.themoah.themoah.common.config.base.BaseTimeWithoutId;
import com.themoah.themoah.domain.team.entity.Team;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Builder(toBuilder = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseTimeWithoutId {
    @Id
    @Column(name = "member_id")
    private String  memberId;
    private String  pwd;
    private String  memberName;
    private String  phoneNumber;
    private String  address;
    private Boolean master;
    private String  authType;


    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "team_id")
    private Team team;

    

} 
