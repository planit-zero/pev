package ai.planit.pev.strategy.chart.object.common;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder
@Getter
@Setter
public class ChartSection {
    private int sectionId;
    private List<ChartEntity> entities;
}
