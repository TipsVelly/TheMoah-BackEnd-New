package com.themoah.themoah.domain.auth.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthRequestDTO {
    private Long authId;
    private String authNm;
    private List<SubAuthRequestDTO>  authRequestDTO;

}
