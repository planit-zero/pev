package ai.planit.pev.strategy.chart.object.common;

import ai.planit.pev.domain.ods.record.dto.Record;
import lombok.*;

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
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Response {
        private List<ChartElement> data;
        private ChartSection headerSection;
        private List<ChartSection> sections;
        private List<String> medicalImages;
        private String htmlData;
    }
}
