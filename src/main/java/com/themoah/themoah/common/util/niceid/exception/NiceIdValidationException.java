package com.themoah.themoah.common.util.niceid.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class NiceIdValidationException extends RuntimeException {
    private com.themoah.themoah.common.util.niceid.dto.enums.NiceIdStatusCode NiceIdStatusCode;
}
