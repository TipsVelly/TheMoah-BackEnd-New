package com.themoah.themoah.domain.menu.service;

import com.themoah.themoah.domain.menu.dto.MenusDTO;
import com.themoah.themoah.domain.menu.dto.SubmenuDTO;
import com.themoah.themoah.domain.menu.entity.Menus;
import com.themoah.themoah.domain.menu.entity.Submenu;
import com.themoah.themoah.domain.menu.repository.MenuRepository;
import com.themoah.themoah.domain.menu.repository.SubmenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MenuService {

    private final MenuRepository menuRepository;
    private final  SubmenuRepository submenuRepository;

    public List<MenusDTO> menusList() {
        List<Menus> oneDep = menuRepository.findAll(Sort.by("menuId"));
        List<MenusDTO> result = new ArrayList<>();

        for (Menus menu : oneDep) {
            MenusDTO menuDTO = new MenusDTO();

            menuDTO.setExpanded(false);
            menuDTO.setMenuNm(menu.getMenuNm());
            menuDTO.setMenuId(menu.getMenuId());
            menuDTO.setMenuKey(menu.getMenuKey());
            menuDTO.setIcon(menu.getIcon());

            Long submenuId = menu.getMenuId();
            List<Submenu> subMenu = submenuRepository.findByMenu_MenuIdOrderBySubmenuIdAsc(submenuId);

            List<SubmenuDTO> subMenuDTOs = new ArrayList<>();

            for (Submenu sub : subMenu) {
                SubmenuDTO subMenuDTO = new SubmenuDTO();
                subMenuDTO.setSubmenuNm(sub.getSubmenuNm());
                subMenuDTO.setSubmenuKey(sub.getSubmenuKey());
                subMenuDTOs.add(subMenuDTO);
            }
            menuDTO.setItems(subMenuDTOs);
            result.add(menuDTO);
        }
        return result;
    }

    // 메뉴 데이터 카운트
    public Long menuCnt() {
        return menuRepository.count();
    }

    // 서브메뉴 데이터 카운트
    public Long subMenuCnt() {
        return submenuRepository.count();
    }
}
