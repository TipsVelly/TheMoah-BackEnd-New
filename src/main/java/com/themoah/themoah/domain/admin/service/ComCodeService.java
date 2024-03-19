package com.themoah.themoah.domain.admin.service;

import com.themoah.themoah.domain.admin.dto.ComCodeAttrDto;
import com.themoah.themoah.domain.admin.dto.ComCodeDeleteRequestDto;
import com.themoah.themoah.domain.admin.dto.ComCodeListDto;
import com.themoah.themoah.domain.admin.entity.ComCodeAttr;
import com.themoah.themoah.domain.admin.entity.ComCodeList;
import com.themoah.themoah.domain.admin.repository.ComCodeAttrRepository;
import com.themoah.themoah.domain.admin.repository.ComCodeListRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ComCodeService {
    private final ComCodeListRepository comCodeListRepository;
    private final ComCodeAttrRepository comCodeAttrRepository;

    public List<ComCodeListDto> getListAndAttrs() {
        List<ComCodeList> comCodeLists = comCodeListRepository.findAll();
        List<ComCodeListDto> comCodeListDtoList = new ArrayList<>();
        for (ComCodeList comCode : comCodeLists) {
            Long comCodeId = comCode.getComCodeId();
            ComCodeListDto comCodeListDto = new ComCodeListDto();
            comCodeListDto.setComCodeId(comCodeId);
            comCodeListDto.setGroupCode(comCode.getGroupCode());
            comCodeListDto.setGroupNm(comCode.getGroupNm());
            comCodeListDto.setUseYn(comCode.getUseYn());
            comCodeListDto.setCUser(comCode.getCUser());
            comCodeListDto.setUUser(comCode.getUUser());
            comCodeListDto.setCDate(comCode.getCDate());
            comCodeListDto.setUDate(comCode.getUDate());
            List<ComCodeAttr> comCodeAttrs = comCodeAttrRepository.findByComCodeList_ComCodeId(comCodeId);
            List<ComCodeAttrDto> attrDtoList = new ArrayList<>();
            for (ComCodeAttr attr : comCodeAttrs) {
                ComCodeAttrDto attrDto = getComCodeAttrDto(attr);
                attrDtoList.add(attrDto);
            }
            comCodeListDto.setComCodeAttrs(attrDtoList);
            comCodeListDtoList.add(comCodeListDto);
        }

        return comCodeListDtoList;
    }
    public boolean saveComCodeAttr(ComCodeAttrDto dto) {
        ComCodeList findList = comCodeListRepository.findById(dto.getComCodeId()).get();
        Optional<ComCodeAttr> attrOptional = comCodeAttrRepository.findByAttrNameAndComCodeList_ComCodeId(dto.getAttrName(), dto.getComCodeId());
        if (attrOptional.isEmpty()) {
            ComCodeAttr attr = comCodeAttrRepository.save(ComCodeAttr.builder()
                    .attrData(dto.getAttrName())
                    .attrName(dto.getAttrName())
                    .comCodeList(findList)
                    .cDate(LocalDateTime.now())
                    .cUser(dto.getCUser())
                    .useYn("Y")
                    .build()
            );
            return true;
        } else {
            throw new RuntimeException("중복된 값이 존재합니다.");
        }
    }

    public void deleteComAttr(ComCodeDeleteRequestDto dto) {
        Long comAttrId = dto.getComAttrId();
        Long comCodeId = dto.getComCodeId();
        comCodeAttrRepository.deleteByComCodeAttrIdAndComCodeList_ComCodeId(comAttrId, comCodeId);
    }

    public Long ComCodeListCount() {
        return comCodeListRepository.count();
    }

    public Long ComCodeAttrCount() {
        return comCodeAttrRepository.count();
    }

    private ComCodeAttrDto getComCodeAttrDto(ComCodeAttr attr) {
        ComCodeAttrDto attrDto = new ComCodeAttrDto();
        attrDto.setComCodeAttrId(attr.getComCodeAttrId());
        attrDto.setAttrData(attr.getAttrData());
        attrDto.setAttrName(attr.getAttrName());
        attrDto.setCUser(attr.getCUser());
        attrDto.setUseYn(attr.getUseYn());
        attrDto.setUDate(attr.getUDate());
        attrDto.setUUser(attr.getUUser());
        attrDto.setCDate(attr.getCDate());
        attrDto.setComCodeId(attr.getComCodeList().getComCodeId());
        return attrDto;
    }


}