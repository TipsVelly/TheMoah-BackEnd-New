package com.themoah.themoah.domain.menu.dto;

import com.themoah.themoah.domain.menu.entity.Menus;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MenusDTO {

    private Long menuId;
    private String menuNm;
    private String menuKey;
    private String icon;

    private boolean expanded;

    private List<SubmenuDTO> items;

    public MenusDTO(Menus menu) {
        this.menuId = menu.getMenuId();
        this.menuNm = menu.getMenuNm();
        this.menuKey = menu.getMenuKey();
        this.icon = menu.getIcon();
    }

}
