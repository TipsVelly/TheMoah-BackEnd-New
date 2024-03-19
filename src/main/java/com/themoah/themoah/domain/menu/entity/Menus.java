package com.themoah.themoah.domain.menu.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Menus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "menu_id")
    private Long menuId;
    private String menuNm;
    private String menuKey;
    private String icon;

    @OneToMany(mappedBy = "menu", cascade = CascadeType.ALL)
    private List<Submenu> submenu;


}
