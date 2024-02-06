package ai.planit.pev.domain.meta.report.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ChartReport {
    private int reportId;
    private String stfNo;
    private String recordInfo;
    private List<ChartReportValue> values;
    private String irb;
    private String rid;
}
