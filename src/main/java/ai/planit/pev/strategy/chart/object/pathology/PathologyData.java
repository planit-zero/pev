package ai.planit.pev.strategy.chart.object.pathology;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class PathologyData {
    @Getter
    @Setter
    public static class Request {
        private String pthlNo;
    }

    @Getter
    @Setter
    public static class Response {
        private List<PathologyContent> pathologyContentList;
        private List<PathologyProcess> pathologyProcessList;
    }
}
