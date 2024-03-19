package com.themoah.themoah.domain.menu.controller;

import java.util.List;

import com.themoah.themoah.domain.menu.dto.MenusDTO;
import com.themoah.themoah.domain.menu.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MenuController {

	private final MenuService menuService;

	// 메뉴 리스트 데이터조회
	@GetMapping("/menu/list")
	public List<MenusDTO> menusList(){
		return menuService.menusList();
	}
}
