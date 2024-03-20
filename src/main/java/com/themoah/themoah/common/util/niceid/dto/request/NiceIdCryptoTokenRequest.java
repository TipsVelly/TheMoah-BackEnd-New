package com.themoah.themoah.common.util.niceid.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class NiceIdCryptoTokenRequest implements Serializable {
    @JsonProperty("dataHeader")
    private DataHeader dataHeader;

    @JsonProperty("dataBody")
    private DataBody dataBody;

    @Data
    public static class DataHeader implements Serializable {
        @JsonProperty("CNTY_CD")
        private String cntyCd;
    }

    @Data
    public static class DataBody implements Serializable {
        @JsonProperty("req_dtim")
        private String reqDtim;

        @JsonProperty("req_no")
        private String reqNo;

        @JsonProperty("enc_mode")
        private String encMode;
    }
}
