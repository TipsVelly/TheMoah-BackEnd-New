package com.themoah.themoah.common.util.niceid.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class NiceIdCryptoTokenResponse {
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
        @JsonProperty("rsp_cd")
        private String rspCd;

        @JsonProperty("site_code")
        private String siteCode;

        @JsonProperty("result_cd")
        private String resultCd;

        @JsonProperty("token_version_id")
        private String tokenVersionId;

        @JsonProperty("token_val")
        private String tokenVal;

        @JsonProperty("period")
        private int period;
    }
}
