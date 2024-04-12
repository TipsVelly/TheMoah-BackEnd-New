package com.themoah.themoah.domain.test.controller;

import com.themoah.themoah.domain.test.dto.TestDetailDto;
import com.themoah.themoah.domain.test.dto.TestDto;
import com.themoah.themoah.domain.test.service.TestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/test")
@Slf4j
public class TestController {

    private final TestService service;

    @GetMapping("/list")
    public ResponseEntity<?> testList(){
        List<TestDto> testDtos = service.testList();
        if(!testDtos.isEmpty()){
            return ResponseEntity.ok(testDtos);
        }else{
            return ResponseEntity.noContent().build();
        }
    }
    @PostMapping("/save")
    public ResponseEntity<?> testSave(@RequestBody TestDto testDto){
        log.info("saveDto = {}",testDto);
        service.testSave(testDto);
        return ResponseEntity.ok().build();
    }
    @PostMapping("/saveDetail")
    public ResponseEntity<?> detailSave(@RequestBody TestDetailDto dto){
        log.info("detailDto={}",dto);
        service.saveDetail(dto);
        return ResponseEntity.ok().build();
    }
}
