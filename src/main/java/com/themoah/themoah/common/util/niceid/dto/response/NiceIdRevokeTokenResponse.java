package com.themoah.themoah.common.util.niceid.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class NiceIdRevokeTokenResponse {
    @JsonProperty("dataHeader")
    private DataHeader dataHeader;

    @JsonProperty("dataBody")
    private DataBody dataBody;

    @Data
    public static class DataHeader {
        @JsonProperty("GW_RSLT_CD")
        private String resultCode;

        @JsonProperty("GW_RSLT_MSG")
        private String resultMessage;
    }

    @Data
    public static class DataBody {
        @JsonProperty("result")
        private boolean result;
    }
}
