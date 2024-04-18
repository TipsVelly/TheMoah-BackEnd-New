package com.themoah.themoah.domain.customer.controller;

import com.themoah.themoah.domain.customer.dto.CustomerDto;
import com.themoah.themoah.domain.customer.service.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/customer")
@Slf4j
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping("/list")
    public ResponseEntity<?> getCustomerList(@RequestParam String industCode){
        log.info("사업장코드 = {}",industCode);
        List<CustomerDto> customerList = customerService.findAll(industCode);
        if(customerList.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(customerList);
    }

}
