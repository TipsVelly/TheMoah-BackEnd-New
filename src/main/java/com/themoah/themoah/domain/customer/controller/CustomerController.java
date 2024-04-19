package com.themoah.themoah.domain.customer.controller;

import com.themoah.themoah.domain.customer.dto.CustomerDto;
import com.themoah.themoah.domain.customer.service.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/customer")
@Slf4j
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping("/list")
    public List<CustomerDto> getCustomerList(@RequestParam("industCode") String industCode){
        log.info("사업장코드 = {}",industCode);
        List<CustomerDto> customerList = customerService.findAll(industCode);
        if(customerList.isEmpty()){
            throw new IllegalArgumentException("거래처가 없음");
        }
        return customerList;
    }
    @PostMapping("/save")
    public void saveCustomer(@RequestBody CustomerDto customerDto){
        log.info("customerDto = {}",customerDto);
        customerService.saveCustomer(customerDto);

    }
    @PostMapping("/test")
    public void test(@RequestBody CustomerDto customerDto){
        log.info("customerDto = {}",customerDto);
    }
    @PostMapping("/update")
    public void updateCustomer(@RequestBody CustomerDto customerDto){
        log.info("customerDto = {}",customerDto);
       customerService.updateCustomer(customerDto);
    }


}
