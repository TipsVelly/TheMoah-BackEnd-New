package com.themoah.themoah.domain.warehouse.repository;

import com.themoah.themoah.domain.warehouse.entity.Warehouse;
import com.themoah.themoah.domain.warehouse.entity.WarehouseId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WarehouseRepository extends JpaRepository<Warehouse, WarehouseId> {

}
