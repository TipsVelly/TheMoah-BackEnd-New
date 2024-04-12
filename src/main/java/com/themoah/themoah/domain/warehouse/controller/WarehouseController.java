package com.themoah.themoah.domain.warehouse.controller;

import com.themoah.themoah.domain.warehouse.dto.WarehouseDTO;
import com.themoah.themoah.domain.warehouse.entity.Warehouse;
import com.themoah.themoah.domain.warehouse.entity.WarehouseId;
import com.themoah.themoah.domain.warehouse.repository.WarehouseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/warehouse", produces = MediaType.APPLICATION_JSON_VALUE)
public class WarehouseController {
    private final WarehouseRepository warehouseRepository;


    @GetMapping("/read")
    public List<WarehouseDTO> getWarehouseInfo() {
        return warehouseRepository.findAll().stream()
                .map(Warehouse::convertToWarehouseDTO)
                .collect(Collectors.toList());
    }

    @PostMapping("/save")
    public WarehouseDTO saveWarehouseInfo(@RequestBody WarehouseDTO reqWarehouseDTO) {
        reqWarehouseDTO.setIndustCode(StringUtils.hasText(reqWarehouseDTO.getIndustCode()) ? reqWarehouseDTO.getIndustCode() : "default");
        long count = warehouseRepository.count();
        Warehouse savedWarehouse = warehouseRepository.save(reqWarehouseDTO.convertToWarehouse(count));
        return savedWarehouse.convertToWarehouseDTO();
    }

    @PostMapping("/delete")
    public void deleteWarehouseInfo(@RequestBody WarehouseDTO reqWarehouseDTO) {
        warehouseRepository.deleteById(WarehouseId.builder()
                        .warehouseCode(reqWarehouseDTO.getWarehouseCode())
                        .industCode(reqWarehouseDTO.getIndustCode())
                .build());
    }
}
