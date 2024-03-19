package com.themoah.themoah.domain.menu.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SubmenuDTO {

    private Long submenuId;
    private String submenuNm;
    private String submenuKey;

    public SubmenuDTO(Long submenuId, String submenuNm, String submenuKey) {
        this.submenuId = submenuId;
        this.submenuNm = submenuNm;
        this.submenuKey = submenuKey;
    }
}
