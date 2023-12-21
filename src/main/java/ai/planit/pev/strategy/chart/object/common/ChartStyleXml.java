package ai.planit.pev.strategy.chart.object.common;

import lombok.Getter;
import lombok.Setter;

public class ChartStyleXml {
    @Getter
    @Setter
    public static class Request {
        private String mdfmClsCd;
        private int mdfmId;
        private int mdfmFomSeq;
        private int mdfmSctnSeq;
    }

    @Getter
    @Setter
    public static class Response {
        private int mdfmSctnSeq;
        private String sctnDgnMetaLdat;
    }
}
