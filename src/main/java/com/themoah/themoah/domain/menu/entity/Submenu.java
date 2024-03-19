package com.themoah.themoah.domain.menu.entity;

import com.themoah.themoah.domain.admin.entity.ComCodeList;
import com.themoah.themoah.domain.team.entity.Team;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Submenu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "submenu_id")
    private Long submenuId;

    private String submenuNm;
    private String submenuKey;

    @ManyToOne
    @JoinColumn(name = "menu_id")
    private Menus menu;


}
