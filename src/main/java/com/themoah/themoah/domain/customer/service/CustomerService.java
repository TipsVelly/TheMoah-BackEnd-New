package com.themoah.themoah.domain.customer.service;

import com.themoah.themoah.domain.customer.dto.CustomerDto;
import com.themoah.themoah.domain.customer.entity.Customer;
import com.themoah.themoah.domain.customer.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;

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
}
