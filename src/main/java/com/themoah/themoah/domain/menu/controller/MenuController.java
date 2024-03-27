package com.themoah.themoah.domain.menu.controller;

import com.themoah.themoah.domain.menu.dto.MenusDTO;
import com.themoah.themoah.domain.menu.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/menu", produces = MediaType.APPLICATION_JSON_VALUE)
public class MenuController {

	private final MenuService menuService;

	// 메뉴 리스트 데이터조회
	@GetMapping("/list")
	public List<MenusDTO> menusList(){
		return menuService.menusList();
	}
}
