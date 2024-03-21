package com.themoah.themoah.domain.admin.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class ComCodeList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "com_code_id")
    private Long comCodeId;
    private String groupCode;
    private String groupNm;
    private String useYn;
    private String cUser;
    private String uUser;
    private LocalDateTime cDate;
    private LocalDateTime uDate;


    @OneToMany(mappedBy = "comCodeList", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ComCodeAttr> comCodeAttrs = new ArrayList<>();

}
