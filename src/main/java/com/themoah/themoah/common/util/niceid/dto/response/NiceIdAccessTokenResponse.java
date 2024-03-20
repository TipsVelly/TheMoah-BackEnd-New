package com.themoah.themoah.common.util.niceid.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class NiceIdAccessTokenResponse {
    @JsonProperty("dataHeader")
    private DataHeader dataHeader;

    @JsonProperty("dataBody")
    private DataBody dataBody;


    @Data
    public static class DataHeader {
        @JsonProperty("GW_RSLT_CD")
        private String resultCode;

        @JsonProperty("TRAN_ID")
        private String transactionId;

        @JsonProperty("GW_RSLT_MSG")
        private String resultMessage;
    }

    @Data
    public static class DataBody {
        @JsonProperty("access_token")
        private String accessToken;

        @JsonProperty("token_type")
        private String tokenType;

        @JsonProperty("expires_in")
        private double expiresIn;

        @JsonProperty("scope")
        private String scope;
    }
}