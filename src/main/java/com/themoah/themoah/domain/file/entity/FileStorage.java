package com.themoah.themoah.domain.file.entity;

import com.themoah.themoah.common.config.base.BaseTime;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PROTECTED;

@Entity
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor(access = PROTECTED)
@Builder
@Getter
public class FileStorage extends BaseTime {


    //파일 그룹 (ex. 팀파일, 멤버파일 등 도메인별로 사용할수 있습니다)
    private String fileGroup;

    //파일 그룹 아이디 (ex. 팀 아이디, 멤버 아이디 등)
    private String groupId;

    private String s3FileUrl;

    private String fileName;



}