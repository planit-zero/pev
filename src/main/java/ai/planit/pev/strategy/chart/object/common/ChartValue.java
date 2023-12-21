package ai.planit.pev.strategy.chart.object.common;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChartValue extends ChartElement {
    public ChartValue(ChartElement element, ChartStyleItem style) {
        super(
                element.getSectionId(),
                element.getId(),
                element.getParentId(),
                element.getMdfmCpemNo(),
                element.getClassType(),
                element.getControlType(),
                element.getMaskingType(),
                element.getContent(),
                element.getDesc(),
                style
        );
    }
}
