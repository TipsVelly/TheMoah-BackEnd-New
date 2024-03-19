package com.themoah.themoah.domain.menu.repository;

import com.themoah.themoah.domain.menu.entity.Menus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuRepository extends JpaRepository<Menus, Long>{

}
