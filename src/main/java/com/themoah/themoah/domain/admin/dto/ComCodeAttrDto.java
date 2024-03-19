package com.themoah.themoah.domain.admin.dto;

import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ComCodeAttrDto {
    private Long comCodeAttrId;
    private String attrName;
    private String attrData;
    private String useYn;
    private String cUser;
    private String uUser;
    private LocalDateTime cDate;
    private LocalDateTime uDate;
    private Long comCodeId;

    public ComCodeAttrDto(ComCodeAttrDto dto,Long comCodeId){
        this.comCodeAttrId = dto.getComCodeAttrId();
        this.attrName = dto.getAttrName();
        this.attrData = dto.getAttrData();
        this.useYn = dto.getUseYn();
        this.cUser = dto.getCUser();
        this.uUser = dto.getUUser();
        this.cDate = dto.getCDate();
        this.uDate = dto.getUDate();
        this.comCodeId = comCodeId;
    }
}
