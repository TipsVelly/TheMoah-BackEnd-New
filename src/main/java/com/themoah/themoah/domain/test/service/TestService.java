package com.themoah.themoah.domain.test.service;

import com.themoah.themoah.domain.test.dto.TestDetailDto;
import com.themoah.themoah.domain.test.dto.TestDto;
import com.themoah.themoah.domain.test.entity.TestDetailEntity;
import com.themoah.themoah.domain.test.entity.TestEntity;
import com.themoah.themoah.domain.test.repository.TestDetailRepository;
import com.themoah.themoah.domain.test.repository.TestRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class TestService {
    private final TestRepository testRepository;
    private final TestDetailRepository testDetailRepository;


    public void testSave(TestDto testDto) {
        TestEntity test = TestEntity.builder()
                .industCode(testDto.getIndustCode())
                .poNo(testDto.getPoNo())
                .poDate(testDto.getPoDate())
                .poAmount(testDto.getPoAmount())
                .divDate(testDto.getDivDate())
                .custCode(testDto.getCustCode())
                .currency(testDto.getCurrency())
                .statBc(testDto.getStatBc())
                .inWh(testDto.getInWh())
                .comment(testDto.getComment())
                .build();
        testRepository.save(test);
    }

    public List<TestDto> testList() {
        Sort sort = Sort.by(Sort.Direction.DESC, "testId");
        List<TestEntity> all = testRepository.findAll(sort);
//        List<TestDetailEntity> detaillist = testDetailRepository.findAll();
//        List<TestDetailDto> dtoList = new ArrayList<>();
//        for (TestDetailEntity testDetailEntity : detaillist) {
//            dtoList.add(TestDetailDto.builder()
//                    .testId(testDetailEntity.getTestEntity().getTestId())
//                    .poPrice(testDetailEntity.getPoPrice())
//                    .poQty(testDetailEntity.getPoQty())
//                    .inQty(testDetailEntity.getInQty())
//                    .itemId(testDetailEntity.getItemId())
//                    .build());
//        }
        return all.stream().map(testEntity -> TestDto.builder()
                .testId(testEntity.getTestId())
                .industCode(testEntity.getIndustCode())
                .poNo(testEntity.getPoNo())
                .poDate(testEntity.getPoDate())
                .poAmount(testEntity.getPoAmount())
                .custCode(testEntity.getCustCode())
                .divDate(testEntity.getDivDate())
                .currency(testEntity.getCurrency())
                .statBc(testEntity.getStatBc())
                .inWh(testEntity.getInWh())
                .comment(testEntity.getComment())
                .testDetailDtos(detailList(testEntity.getTestId()))
                .build()).toList();
    }
    @Transactional
    public void saveDetail(TestDetailDto dto){
        Optional<TestEntity> testEntity = testRepository.findById(dto.getTestId());
        if(testEntity.isEmpty()){
            throw new RuntimeException("테스트 저장 실패");
        }else{
            int poAmount = testEntity.get().getPoAmount();
            int newValue = poAmount + (dto.getPoQty()*dto.getPoPrice());

            log.info("testEntity : {}", testEntity);
            testEntity.get().setPoAmount(newValue);
            TestDetailEntity detail = TestDetailEntity.builder()
                    .poPrice(dto.getPoPrice())
                    .poQty(dto.getPoQty())
                    .inQty(dto.getInQty())
                    .itemId(dto.getItemId())
                    .testEntity(testEntity.get())
                    .build();

            testDetailRepository.save(detail);
        }

    }
    public List<TestDetailDto> detailList(Long id){
        List<TestDetailEntity> byTestEntityTestId = testDetailRepository.findByTestEntity_TestId(id);
        return byTestEntityTestId.stream().map(testDetailEntity -> TestDetailDto.builder()
                .testId(testDetailEntity.getTestEntity().getTestId())
                .poPrice(testDetailEntity.getPoPrice())
                .poQty(testDetailEntity.getPoQty())
                .inQty(testDetailEntity.getInQty())
                .itemId(testDetailEntity.getItemId())
                .build()).toList();
    }

}
