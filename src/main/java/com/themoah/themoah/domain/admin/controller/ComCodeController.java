package com.themoah.themoah.domain.admin.controller;
import com.themoah.themoah.domain.admin.dto.ComCodeAttrDto;
import com.themoah.themoah.domain.admin.dto.ComCodeDeleteRequestDto;
import com.themoah.themoah.domain.admin.dto.ComCodeListDto;
import com.themoah.themoah.domain.admin.service.ComCodeService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/comCode")
@Slf4j
public class ComCodeController {

    private final ComCodeService comCodeService;

    @Operation(summary = "Get ComCode List", description = "코드 목록 및 속성을 가져옵니다.")
    @GetMapping("/getComCodeList")
    public ResponseEntity<?> getComCodeList() {
        List<ComCodeListDto> comCodeListDtoList = comCodeService.getListAndAttrs();
        if (comCodeListDtoList != null) {
            return ResponseEntity.ok(comCodeListDtoList);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Create ComCode Attribute", description = "코드 속성을 생성합니다.")
    @PostMapping("/createComCodeAttr")
    public boolean createComCodeAttr(@RequestBody ComCodeAttrDto dto){
        return comCodeService.saveComCodeAttr(dto);
    }

    @Operation(summary = "Delete ComCode Attribute", description = "코드 속성을 삭제합니다.")
    @PostMapping("/deleteComCodeAttr")
    public void deleteComCodeAttr(@RequestBody ComCodeDeleteRequestDto dto){
        comCodeService.deleteComAttr(dto);
    }
}
