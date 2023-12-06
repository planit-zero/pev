package ai.planit.pev.strategy.chart.object.common;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class Chart {
    private ChartSection headerSection;
    private List<ChartSection> sections;
}
