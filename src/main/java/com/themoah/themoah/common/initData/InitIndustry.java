package com.themoah.themoah.common.initData;

import com.themoah.themoah.common.constant.init.InitData;
import com.themoah.themoah.domain.industry.entity.Industry;
import com.themoah.themoah.domain.industry.repository.IndustryRepository;
import com.themoah.themoah.domain.warehouse.entity.Warehouse;
import com.themoah.themoah.domain.warehouse.entity.WarehouseId;
import com.themoah.themoah.domain.warehouse.repository.WarehouseRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;

import java.util.ArrayList;
import java.util.List;

@Configuration
@Profile("dev")
@Slf4j
@RequiredArgsConstructor
public class InitIndustry {
    private final IndustryRepository industryRepository;
    private final WarehouseRepository warehouseRepository;
    @Bean
    @Order(5)
    public ApplicationRunner initIndustryData () {
        return args -> {
            log.info("initIndustryData Start");
            boolean existsed = industryRepository.existsById(InitData.INDUST_CODE.getValue());
            if(!existsed) {
                Industry savedIndustry = industryRepository.save(Industry.builder()
                        .useYn("Y")
                        .industCode("init_industry_01")
                        .industAddr("서울특별시 금산구 1080로")
                        .industName("디폴트사업장")
                        .build());

                List<Warehouse> warehouseList = new ArrayList<>();
                for (int i = 0; i <10; i++) {
                    warehouseList.add(Warehouse.builder()
                                    .warehouseId(WarehouseId.builder()
                                            .warehouseCode("wh_0" + (i+1))
                                            .industCode(savedIndustry.getIndustCode())
                                            .build())
                                    .warehouseName("디폴트 창고" + (i+1))
                                    .matYn("Y")
                                    .partYn("Y")
                                    .prodYn("Y")
                                    .vmiYn("Y")
                                    .rtnYn("Y")
                                    .saleYn("Y")
                                    .subYn("Y")
                                    .badYn("N")
                                    .inspYn("Y")
                                    .minusYn("Y")
                                    .useYn("Y")
                                    .custCode("samsung-warehouse-105")
                                    .teamCode("team_01")
                                    .dispSeq((long) (i + 1))
                                    .comment("디폴트 창고 " + (i+1) + "번째 입니다.")
                                    .industry(savedIndustry)
                            .build());
                }

                warehouseRepository.saveAll(warehouseList);
            }
            log.info("initIndustryData End");
        };
    }

}
