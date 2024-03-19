package com.themoah.themoah.domain.admin.entity;

import com.themoah.themoah.domain.member.entity.Member;
import com.themoah.themoah.domain.menu.entity.Submenu;
import com.themoah.themoah.domain.team.entity.Team;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static lombok.AccessLevel.PROTECTED;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class ComCodeList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "com_code_id")
    private Long comCodeId;
    private String groupCode;
    @Column(unique = true)
    private String groupNm;
    private String useYn;
    private String cUser;
    private String uUser;
    private LocalDateTime cDate;
    private LocalDateTime uDate;


    @OneToMany(mappedBy = "comCodeList", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ComCodeAttr> comCodeAttrs = new ArrayList<>();

}
