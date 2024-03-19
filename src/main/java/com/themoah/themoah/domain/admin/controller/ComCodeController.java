package com.themoah.themoah.domain.admin.controller;

import com.themoah.themoah.domain.admin.dto.ComCodeAttrDto;
import com.themoah.themoah.domain.admin.dto.ComCodeDeleteRequestDto;
import com.themoah.themoah.domain.admin.dto.ComCodeListDto;
import com.themoah.themoah.domain.admin.service.ComCodeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comCode")
@Slf4j
public class ComCodeController {

    private final ComCodeService comCodeService;

    @GetMapping("/getComCodeList")
    public ResponseEntity<?> getComCodeList() {
        List<ComCodeListDto> comCodeListDtoList = comCodeService.getListAndAttrs();
        if (comCodeListDtoList != null) {
            return ResponseEntity.ok(comCodeListDtoList);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @PostMapping("/createComCodeAttr")
    public boolean createComCodeAttr(@RequestBody ComCodeAttrDto dto){

        return comCodeService.saveComCodeAttr(dto);
    }

    @PostMapping("/deleteComCodeAttr")
    public void deleteComCodeAttr(@RequestBody ComCodeDeleteRequestDto dto){
        comCodeService.deleteComAttr(dto);
    }
}





