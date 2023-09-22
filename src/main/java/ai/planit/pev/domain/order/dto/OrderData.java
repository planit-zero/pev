package ai.planit.pev.domain.order.dto;

import lombok.Getter;
import lombok.Setter;

public class OrderData {

    @Getter
    @Setter
    public static class Request {
        private String ptNo;
        private String medPactTpCd;
        private String ordDt;
        private String odaplPopCd;
        private String fsrStfNo;
    }

    @Getter
    @Setter
    public static class Response {
        private String ordNm;
        private String fsrDtm;
        private String fmtInfo;
    }
}
