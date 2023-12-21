package ai.planit.pev.strategy.chart.object.common;

import ai.planit.pev.strategy.chart.constant.ChartClassType;
import ai.planit.pev.strategy.chart.constant.ChartControlType;
import ai.planit.pev.strategy.chart.constant.ChartMaskingType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChartElement {
    private int sectionId;
    private String id;
    private String parentId;
    private String mdfmCpemNo;
    private ChartClassType classType;
    private ChartControlType controlType;
    private ChartMaskingType maskingType;
    private String content;
    private String desc;
    private ChartStyleItem style;
}
