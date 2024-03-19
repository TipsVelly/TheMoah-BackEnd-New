package com.themoah.themoah.domain.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ComCodeDeleteRequestDto {
    private Long comCodeId;
    private Long comAttrId;
}
