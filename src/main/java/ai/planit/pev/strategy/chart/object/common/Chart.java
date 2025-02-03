package ai.planit.pev.strategy.chart.object.common;

import ai.planit.pev.domain.ods.record.dto.Record;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

public class Chart {
    @Getter
    @Setter
    public static class Request {
        private String maskingYn;
        private Record.Response record;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class Response {
        private List<ChartElement> data;
        private ChartSection headerSection;
        private List<ChartSection> sections;
        private List<String> medicalImages;
    }
}
