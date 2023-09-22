package ai.planit.pev.domain.order.dto;

import lombok.Getter;
import lombok.Setter;

public class OrderData {

    @Getter
    @Setter
    public static class Request {
        private String ptNo; // 환자병록번호
        private String medPactTpCd; // 환자구분코드
        private String ordDt; // 처방일자
        private String odaplPopCd; // 처방적용목적코드
        private String fsrStfNo; // 최초등록직원번호
    }

    @Getter
    @Setter
    public static class Response {
        private String ordNm; // 처방명
        private String fsrDtm; // 최초등록일시
        private String fmtInfo; // 수행사인 정보
    }
}
