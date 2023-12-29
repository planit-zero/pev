package ai.planit.pev.strategy.chart.object.order;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class OrderSection {
    @Getter
    @Setter
    public static class Request {
        private String ptNo;
        private String medPactTpCd;
        private String ordDt;
        private String writingDeptCd;
    }

    @Getter
    @Setter
    public static class Response {
        private String ptNo;
        private String medPactTpCd;
        private String ordDt;
        private String odaplPopCd;
        private String odaplPopNm;
        private String fsrStfNo;
        private String fsrStfNm;
        private List<OrderContent> contents;
    }
}
