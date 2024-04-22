package com.themoah.themoah.domain.industry.controller;

import com.themoah.themoah.domain.industry.dto.IndustryDTO;
import com.themoah.themoah.domain.industry.entity.Industry;
import com.themoah.themoah.domain.industry.repository.IndustryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/industry")
public class IndustryController {
    private final IndustryRepository industryRepository;

    @GetMapping("/read/init")
    public IndustryDTO getInitIndustry() {
        return industryRepository.findById("init_industry_01").map(Industry::toDTO).stream().findFirst().get();
    }
}
