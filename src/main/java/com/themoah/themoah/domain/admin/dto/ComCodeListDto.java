package com.themoah.themoah.domain.admin.dto;

import com.themoah.themoah.domain.admin.entity.ComCodeAttr;
import com.themoah.themoah.domain.admin.entity.ComCodeList;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ComCodeListDto {
    private Long comCodeId;
    private String groupCode;
    private String groupNm;
    private String useYn;
    private String cUser;
    private String uUser;
    private LocalDateTime cDate;
    private LocalDateTime uDate;
    private List<ComCodeAttrDto> comCodeAttrs;

    public ComCodeListDto(ComCodeList comCodeList,List<ComCodeAttrDto> attrDto){
        this.comCodeId = comCodeList.getComCodeId();
        this.groupCode = comCodeList.getGroupCode();
        this.groupNm = comCodeList.getGroupNm();
        this.useYn = comCodeList.getUseYn();
        this.cUser = comCodeList.getCUser();
        this.uUser = comCodeList.getUUser();
        this.cDate = comCodeList.getCDate();
        this.uDate = comCodeList.getUDate();
        this.comCodeAttrs = attrDto;
    }
}
