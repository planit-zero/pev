package ai.planit.pev.strategy.chart.object.common;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ChartAttribute extends ChartElement {
    private List<ChartAttribute> attributes;
    private List<ChartValue> values;

    public ChartAttribute(ChartElement element) {
        super(
                element.getSectionId(),
                element.getId(),
                element.getParentId(),
                element.getClassType(),
                element.getControlType(),
                element.getMaskingType(),
                element.getContent(),
                element.getDesc()
        );
    }
}
