package ai.planit.pev.domain.order.dto;

import lombok.Getter;
import lombok.Setter;

public class OrderSection {

    @Getter
    @Setter
    public static class Request {
        private String ptNo;
        private String medPactTpCd;
        private String ordDt;
    }

    @Getter
    @Setter
    public static class Response {
        private String odaplPopCd;
        private String odaplPopNm;
        private String fsrStfNo;
        private String fsrStfNm;
    }
}
