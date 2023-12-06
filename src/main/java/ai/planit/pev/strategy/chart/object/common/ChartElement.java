package ai.planit.pev.strategy.chart.object.common;

import ai.planit.pev.strategy.chart.constant.ChartClassType;
import ai.planit.pev.strategy.chart.constant.ChartControlType;
import ai.planit.pev.strategy.chart.constant.ChartMaskingType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class ChartElement {
    private int sectionId;
    private String id;
    private String parentId;
    private ChartClassType classType;
    private ChartControlType controlType;
    private ChartMaskingType maskingType;
    private String content;
    private String desc;
}
