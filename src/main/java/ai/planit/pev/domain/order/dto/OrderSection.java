package ai.planit.pev.domain.order.dto;

import lombok.Getter;
import lombok.Setter;

public class OrderSection {

    @Getter
    @Setter
    public static class Request {
        private String ptNo; // 환자병록번호
        private String medPactTpCd; // 환자구분코드
        private String ordDt; // 처방일자
    }

    @Getter
    @Setter
    public static class Response {
        private String odaplPopCd; // 처방적용목적코드
        private String odaplPopNm; // 처방적용목적명
        private String fsrStfNo; // 최초등록직원번호
        private String fsrStfNm; // 최초등록직원명
    }
}
