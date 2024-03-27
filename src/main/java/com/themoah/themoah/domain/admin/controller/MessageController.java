package com.themoah.themoah.domain.admin.controller;

import com.themoah.themoah.domain.admin.dto.MessageDto;
import com.themoah.themoah.domain.admin.service.MessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/message")
@RequiredArgsConstructor
@Slf4j
public class MessageController {
    private final MessageService service;

    @PostMapping("/save")
    public void save(@RequestBody MessageDto dto){
        log.info("dto={}",dto);
        service.save(dto);

    }
    @GetMapping("/list")
    public List<MessageDto> list(){
        return service.findAll();
    }


}
