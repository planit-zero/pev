package ai.planit.pev.strategy.chart.object.medical;

import ai.planit.pev.domain.ods.record.dto.Record;
import ai.planit.pev.strategy.chart.object.common.Chart;
import lombok.Getter;
import lombok.Setter;

public class MedicalReply {
    @Getter
    @Setter
    public static class Request {
        private int mdrcId;
        private int mdrcFomSeq;
        private String maskingYn;
    }

    @Getter
    @Setter
    public static class Response {
        private Chart.Response chart;
        private Record.Response record;
    }
}
