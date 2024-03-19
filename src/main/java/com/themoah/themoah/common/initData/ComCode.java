package com.themoah.themoah.common.initData;

import com.themoah.themoah.domain.admin.entity.ComCodeAttr;
import com.themoah.themoah.domain.admin.entity.ComCodeList;
import com.themoah.themoah.domain.admin.repository.ComCodeAttrRepository;
import com.themoah.themoah.domain.admin.repository.ComCodeListRepository;
import com.themoah.themoah.domain.admin.service.ComCodeService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;

import java.time.LocalDateTime;

import static java.time.LocalDateTime.now;

@Configuration
@Profile("dev")
@Slf4j
@RequiredArgsConstructor
public class ComCode {

    private final ComCodeListRepository comCodeListRepository;
    private final ComCodeAttrRepository comCodeAttrRepository;
    private final ComCodeService service;

    @Bean
    @Order(4)
    public ApplicationRunner initComCodeData() {
        return args -> {
            log.info("initComCodeData Start");
            if (service.ComCodeListCount() == 0) {
                initComCode();
            }
            log.info("initComCodeData End");
        };
    }

    @Transactional
    private void initComCode() {
        ComCodeList asnCodeList = ComCodeList.builder()
                .groupCode("WARE_ASN")
                .groupNm("입고상태")
                .cUser("tester")
                .cDate(now())
                .useYn("Y")
                .build();

        ComCodeList savedAsnComcodeList = comCodeListRepository.save(asnCodeList);

        ComCodeList dnCodeList = ComCodeList.builder()
                .groupCode("WARE_DN")
                .groupNm("출고상태")
                .cUser("tester")
                .cDate(now())
                .useYn("Y")
                .build();
        ComCodeList savedDnComCodeList = comCodeListRepository.save(dnCodeList);

        ComCodeAttr asnCodeAttr1 = ComCodeAttr.builder()
                .attrName("입고예정")
                .attrData("입고예정")
                .cDate(now())
                .useYn("Y")
                .cUser("tester")
                .comCodeList(savedAsnComcodeList)
                .build();
        comCodeAttrRepository.save(asnCodeAttr1);

        ComCodeAttr asnCodeAttr2 = ComCodeAttr.builder()
                .attrName("입고준비")
                .attrData("입고준비")
                .cDate(now())
                .useYn("Y")
                .cUser("tester")
                .comCodeList(savedAsnComcodeList)
                .build();
        comCodeAttrRepository.save(asnCodeAttr2);

        ComCodeAttr asnCodeAttr3 = ComCodeAttr.builder()
                .attrName("입고 중")
                .attrData("입고 중")
                .cDate(now())
                .useYn("Y")
                .cUser("tester")
                .comCodeList(savedAsnComcodeList)
                .build();
        comCodeAttrRepository.save(asnCodeAttr3);
        
        ComCodeAttr asnCodeAttr4 = ComCodeAttr.builder()
                .attrName("입고완료")
                .attrData("입고완료")
                .cDate(now())
                .useYn("Y")
                .cUser("tester")
                .comCodeList(savedAsnComcodeList)
                .build();
        comCodeAttrRepository.save(asnCodeAttr4);
        
        ComCodeAttr dnCodeAttr1 = ComCodeAttr.builder()
                .attrName("출고예정")
                .attrData("출고예정")
                .cDate(now())
                .useYn("Y")
                .cUser("tester")
                .comCodeList(savedDnComCodeList)
                .build();
        comCodeAttrRepository.save(dnCodeAttr1);

        ComCodeAttr dnCodeAttr2 = ComCodeAttr.builder()
                .attrName("출고완료")
                .attrData("출고완료")
                .cDate(now())
                .useYn("Y")
                .cUser("tester")
                .comCodeList(savedDnComCodeList)
                .build();
        comCodeAttrRepository.save(dnCodeAttr2);
    }
}
