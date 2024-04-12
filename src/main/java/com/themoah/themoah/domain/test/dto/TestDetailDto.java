package com.themoah.themoah.domain.test.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TestDetailDto {
    private Long testId;
    private int poPrice;//발주단가
    private int poQty;//발주수량
    private int inQty;//재고수량
    private int itemId;//품목ID
}
