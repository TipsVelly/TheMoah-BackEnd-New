package com.themoah.themoah.domain.menu.repository;

import com.themoah.themoah.domain.menu.entity.Submenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubmenuRepository extends JpaRepository<Submenu, Long> {

    // menuId 로 submenu 찾기
    List<Submenu> findByMenu_MenuIdOrderBySubmenuIdAsc(Long submenuId);
}
