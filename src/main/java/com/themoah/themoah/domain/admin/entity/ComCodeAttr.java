package com.themoah.themoah.domain.admin.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class ComCodeAttr {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "com_attr_id")
    private Long comCodeAttrId;
    @Column(unique = true)
    private String attrName;
    private String attrData;
    private String useYn;
    private String cUser;
    private String uUser;
    private LocalDateTime cDate;
    private LocalDateTime uDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "com_code_id")
    private ComCodeList comCodeList;
}
