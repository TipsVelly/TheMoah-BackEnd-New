package com.themoah.themoah.domain.customer.service;

import com.themoah.themoah.domain.customer.dto.CustomerDto;
import com.themoah.themoah.domain.customer.entity.Customer;

import com.themoah.themoah.domain.customer.entity.CustomerId;
import com.themoah.themoah.domain.customer.repository.CustomerRepository;
import com.themoah.themoah.domain.industry.entity.Industry;
import com.themoah.themoah.domain.industry.repository.IndustryRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor

@Slf4j
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final IndustryRepository industryRepository;
    private static int CUSTOMER_SEQ = 1;



    public List<CustomerDto> findAll(String industCode) {
        List<Customer> findCustomerList = customerRepository.findByIndustry_IndustCode(industCode);
        List<CustomerDto> customerDtoList = new ArrayList<>();
        for (Customer customer : findCustomerList) {
            customerDtoList.add(CustomerDto.builder()
                    .custCode(customer.getCustomerId().getCustCode())
                    .industCode(customer.getCustomerId().getIndustCode())
                    .custName(customer.getCustName())
                    .prtName(customer.getPrtName())
                    .custBc(customer.getCustBc())
                    .custKd(customer.getCustKd())
                    .fromDate(customer.getFromDate())
                    .toDate(customer.getToDate())
                    .salYn(customer.getSalYn())
                    .purYn(customer.getPurYn())
                    .deBc(customer.getDeBc())
                    .dlBc(customer.getDlBc())
                    .taxBc(customer.getTaxBc())
                    .issBc(customer.getIssBc())
                    .salCust(customer.getSalCust())
                    .purCust(customer.getPurCust())
                    .pSetBc(customer.getPSetBc())
                    .dayBc(customer.getDayBc())
                    .payBc(customer.getPayBc())
                    .currency(customer.getCurrency())
                    .currency2(customer.getCurrency2())
                    .zipCode(customer.getZipCode())
                    .add1(customer.getAdd1())
                    .add2(customer.getAdd2())
                    .addPrt(customer.getAddPrt())
                    .tel(customer.getTel())
                    .fax(customer.getFax())
                    .email(customer.getEmail())
                    .homepage(customer.getHomepage())
                    .esero(customer.getEsero())
                    .bizNo(customer.getBizNo())
                    .eTel(customer.getETel())
                    .bizType(customer.getBizType())
                    .bizKind(customer.getBizKind())
                    .ceoName(customer.getCeoName())
                    .items(customer.getItems())
                    .acctYn(customer.getAcctYn())
                    .bankBc(customer.getBankBc())
                    .acctName(customer.getAcctName())
                    .acctNo(customer.getAcctNo())
                    .coNo(customer.getCoNo())
                    .useYn(customer.getUseYn())
                    .inspBc(customer.getInspBc())
                    .empNo(customer.getEmpNo())
                    .comment(customer.getComment())
                    .cDate(customer.getCDate())
                    .cUser(customer.getCUser())
                    .uDate(customer.getUDate())
                    .uUser(customer.getUUser())
                    .build());
        }
        for (CustomerDto customerDto : customerDtoList) {
            if (customerDto.getSalYn().equals("Y") && customerDto.getPurYn().equals("Y")) {
                customerDto.setSalPur("매입/매출");
            } else if(customerDto.getSalYn().equals("Y")){
                customerDto.setSalPur("매출");
            } else if(customerDto.getPurYn().equals("Y")){
                customerDto.setSalPur("매입");
            }
        }

        return customerDtoList;
    }

    public void saveCustomer(CustomerDto customerDto){
        Industry industry = industryRepository.findById(customerDto.getIndustCode())
                .orElseThrow(() -> new EntityNotFoundException("Industry not found"));
        CustomerId custId = CustomerId.builder()
                .custCode("custCode"+CUSTOMER_SEQ++)
                .industCode(industry.getIndustCode())
                .build();
        log.info("custId = {}",custId.toString());
        Customer customer =  Customer.builder()
                .customerId(custId)
                .custBc(customerDto.getCustBc())
                .cDate(LocalDateTime.now())
                .cUser(customerDto.getCUser())
                .custKd(customerDto.getCustKd())
                .esero(customerDto.getEsero())
                .tel(customerDto.getTel())
                .empNo(customerDto.getEmpNo())
                .custName(customerDto.getCustName())
                .salYn(customerDto.getSalYn())
                .purYn(customerDto.getPurYn())
                .industry(industry)
                .build();
        customerRepository.save(customer);
    }


    public void updateCustomer(CustomerDto customerDto){
        CustomerId customerId =  CustomerId.builder()
                .custCode(customerDto.getCustCode())
                .industCode(customerDto.getIndustCode())
                .build();
        Optional<Industry> industryOptional = industryRepository.findById(customerDto.getIndustCode());

        Optional<Customer> optionalCustomer = customerRepository.findById(customerId);
        if(optionalCustomer.isPresent()&&industryOptional.isPresent()){
            optionalCustomer.get().updateCustomer(customerDto);
            optionalCustomer.get().setCustomerIndustry(industryOptional.get());
            customerRepository.save(optionalCustomer.get());
        } else {
            throw new EntityNotFoundException("Customer not found");
        }
    }

}
