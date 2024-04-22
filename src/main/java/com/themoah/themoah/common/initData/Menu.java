package com.themoah.themoah.common.initData;

import com.themoah.themoah.domain.menu.entity.Menus;
import com.themoah.themoah.domain.menu.entity.Submenu;
import com.themoah.themoah.domain.menu.repository.MenuRepository;
import com.themoah.themoah.domain.menu.repository.SubmenuRepository;
import com.themoah.themoah.domain.menu.service.MenuService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;

import java.util.ArrayList;
import java.util.List;

@Configuration
@Profile("dev")
@Slf4j
@RequiredArgsConstructor
public class Menu {
    private static Long DEFAULT_MENU_ID = 0L;
    private static Long DEFAULT_SUBMENU_ID = 0L;

    private final MenuService menuService;
    private final MenuRepository menuRepository;
    private final SubmenuRepository submenuRepository;

    @Bean
    @Order(3)
    public ApplicationRunner initMenu(){
        return args -> {
            log.info("init menu Start");
            if (menuService.menuCnt() == 0) {
                initMenus();
            }
            log.info("init menu End");
        };
    }

    // 초기 상위 메뉴 데이터 생성
    private void initMenus() {
        Long menuCnt = menuService.menuCnt();
        Long subCnt = 1L;

/********************************* Home(홈) *********************************/
        Menus menuHome = Menus.builder()
                .menuId(Long.valueOf(1))
                .menuNm("Home")
                .icon("sap-icon://home")
                .build();
        menuRepository.save(menuHome);

/********************************* SalesMgmt(영업) *********************************/
        Menus menuSales = Menus.builder()
                .menuId(Long.valueOf(2))
                .menuNm("영업")
                .menuKey("saleMgmt")
                .icon("sap-icon://sales-order-item").
                build();
        menuRepository.save(menuSales);

        List<Submenu> submenuSales = new ArrayList<>();

        submenuSales.add(
                Submenu.builder()
                        .menu(menuSales)
                        .submenuId(subCnt++)
                        .submenuNm("발주")
                        .submenuKey("orderMgmt")
                        .build()
        );

        submenuSales.add(
                Submenu.builder()
                        .menu(menuSales)
                        .submenuId(subCnt++)
                        .submenuNm("견적")
                        .submenuKey("estOrder")
                        .build()
        );


        for(Submenu oneSubMenu : submenuSales){
            submenuRepository.save(oneSubMenu);
        }


/********************************* PdctMgmt(구매) *********************************/

        List<Submenu> submenuPdct = new ArrayList<>();
        Menus menuPdct = Menus.builder()
                .menuId(Long.valueOf(3))
                .menuNm("구매")
                .menuKey("pdctMgmt")
                .icon("sap-icon://shipping-status")
                .build();
        menuRepository.save(menuPdct);

        submenuPdct.add(
                Submenu.builder().
                        menu(menuPdct).
                        submenuId(subCnt++).
                        submenuNm("납품").
                        submenuKey("deliveryMgmt").
                        build()
        );
        submenuPdct.add(
                Submenu.builder().
                        menu(menuPdct).
                        submenuId(subCnt++).
                        submenuNm("생산").
                        submenuKey("prodMgmt").
                        build()
        );

        for(Submenu oneSubMenu : submenuPdct){
            submenuRepository.save(oneSubMenu);
        }


/********************************* SupplyMgmt(창고) ********************************/
        List<Submenu> submenuSupply = new ArrayList<>();
        Menus menuSupply = Menus.builder().
                menuId(Long.valueOf(4)).
                menuNm("창고").
                menuKey("supplyMgmt").
                icon("sap-icon://supplier").
                build();
        menuRepository.save(menuSupply);

        submenuSupply.add(
                Submenu.builder().
                        menu(menuSupply).
                        submenuId(subCnt++).
                        submenuNm("입고").
                        submenuKey("asnMgmt").
                        build()
        );
        submenuSupply.add(
                Submenu.builder().
                        menu(menuSupply).
                        submenuId(subCnt++).
                        submenuNm("출고").
                        submenuKey("dnMgmt").
                        build()
        );
        submenuSupply.add(
                Submenu.builder().
                        menu(menuSupply).
                        submenuId(subCnt++).
                        submenuNm("조정").
                        submenuKey("adjustMgmt").
                        build()
        );

        for(Submenu oneSubMenu : submenuSupply){
            submenuRepository.save(oneSubMenu);
        }


/********************************* InfoMgmt(기준정보) ********************************/
        List<Submenu> submenuInfo = new ArrayList<>();
        Menus menuInfo = Menus.builder().
                menuId(Long.valueOf(5)).
                menuNm("기준정보").
                menuKey("infoMgmt").
                icon("sap-icon://manager-insight").
                build();
        menuRepository.save(menuInfo);

        submenuInfo.add(
                Submenu.builder().
                        menu(menuInfo).
                        submenuId(subCnt++).
                        submenuNm("사업장관리").
                        submenuKey("industryMgmt").
                        build()
        );

        submenuInfo.add(
                Submenu.builder().
                        menu(menuInfo).
                        submenuId(subCnt++).
                        submenuNm("거래처").
                        submenuKey("customerMgmt").
                        build()
        );

        submenuInfo.add(
                Submenu.builder().
                        menu(menuInfo).
                        submenuId(subCnt++).
                        submenuNm("품목").
                        submenuKey("gdsMgmt").
                        build()
        );
        submenuInfo.add(
                Submenu.builder().
                        menu(menuInfo).
                        submenuId(subCnt++).
                        submenuNm("배송").
                        submenuKey("delivery").
                        build()
        );
        submenuInfo.add(
                Submenu.builder().
                        menu(menuInfo).
                        submenuId(subCnt++).
                        submenuNm("창고").
                        submenuKey("warehouseReg").
                        build()
        );

        for(Submenu oneSubMenu : submenuInfo){
            submenuRepository.save(oneSubMenu);
        }


/********************************* Admin(관리자) ********************************/

        List<Submenu> submenuAdmin = new ArrayList<>();
        Menus menuAdmin = Menus.builder().
                menuId(Long.valueOf(6)).
                menuNm("관리자").
                menuKey("admin").
                icon("sap-icon://action-settings").
                build();
        menuRepository.save(menuAdmin);

        submenuAdmin.add(
                Submenu.builder().
                        menu(menuAdmin).
                        submenuId(subCnt++).
                        submenuNm("팀설정").
                        submenuKey("teamMgmt").
                        build()
        );
        submenuAdmin.add(
                Submenu.builder().
                        menu(menuAdmin).
                        submenuId(subCnt++).
                        submenuNm("권한설정").
                        submenuKey("authMgmt").
                        build()
        );
        submenuAdmin.add(
                Submenu.builder().
                        menu(menuAdmin).
                        submenuId(subCnt++).
                        submenuNm("환경설정").
                        submenuKey("comCodeMgmt").
                        build()
        );

        for(Submenu oneSubMenu : submenuAdmin){
            submenuRepository.save(oneSubMenu);
        }
    }

/*
    private void initMenus() {
        Map<String, Menus> menusMap = new HashMap<>();

        menusMap.put("Home", createMenu(DEFAULT_MENU_ID++, "Home", null,"sap-icon://home"));
        menusMap.put("영업관리", createMenu(DEFAULT_MENU_ID++, "영업관리", "saleMgmt", "sap-icon://sales-order-item"));
        menusMap.put("구매관리", createMenu(DEFAULT_MENU_ID++, "구매관리", "pdctMgmt", "sap-icon://shipping-status"));
        menusMap.put("공급관리", createMenu(DEFAULT_MENU_ID++, "공급관리", "supplyMgmt", "sap-icon://supplier"));
        menusMap.put("기준정보관리", createMenu(DEFAULT_MENU_ID++, "기준정보관리", "infoMgmt", "sap-icon://manager-insight"));
        menusMap.put("관리자", createMenu(DEFAULT_MENU_ID++, "관리자", "admin", "sap-icon://action-settings"));

        menusMap.forEach((key, menu) -> {
            menuRepository.save(menu);
            saveSubmenus(menu, key);
        });
    }

    private Menus createMenu(Long menuId, String menuName, String menuKey, String icon) {
        return Menus.builder()
                .menuId(menuId)
                .menuNm(menuName)
                .menuKey(menuKey)
                .icon(icon)
                .build();
    }

    private void saveSubmenus(Menus menu, String menuName) {
        List<Submenu> submenus = new ArrayList<>();
        submenus.add(createSubmenu(menu, DEFAULT_SUBMENU_ID++, "견적요청", "estOrder"));
        // Add other submenus for respective menus

        submenus.forEach(submenu -> {
            submenuRepository.save(submenu);
        });
    }

    private Submenu createSubmenu(Menus menu, Long submenuId, String submenuName, String submenuKey) {
        return Submenu.builder()
                .menu(menu)
                .submenuId(submenuId)
                .submenuNm(submenuName)
                .submenuKey(submenuKey)
                .build();
    }


 */
}
