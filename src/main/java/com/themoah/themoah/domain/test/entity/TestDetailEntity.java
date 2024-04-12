package com.themoah.themoah.domain.test.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TestDetailEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long testDetailId;
    private int poPrice;//발주단가
    private int poQty;//발주수량
    private int inQty;//재고수량
    private int itemId;//품목ID
    @ManyToOne
    @JoinColumn(name = "test_id")
    private TestEntity testEntity;


}
