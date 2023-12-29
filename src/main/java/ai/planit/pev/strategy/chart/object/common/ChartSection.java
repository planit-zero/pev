package ai.planit.pev.strategy.chart.object.common;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ChartSection {
    private int sectionId;
    private List<ChartEntity> entities;
    private ChartStyleSection style;
}
