package ai.planit.pev.domain.meta.report.dto;

import ai.planit.pev.strategy.chart.constant.ChartClassType;
import ai.planit.pev.strategy.chart.constant.ChartControlType;
import ai.planit.pev.strategy.chart.constant.ChartMaskingType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChartReportValue {
    private int sectionId;
    private String id;
    private String parentId;
    private ChartClassType classType;
    private ChartControlType controlType;
    private ChartMaskingType maskingType;
    private String content;
    private String desc;
    private String confirmYn;
    private String report;
}
