package ai.planit.pev.strategy.chart.object.common;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ChartData {
    private List<ChartElement> values;

    public ChartData(List<ChartElement> values) {
        this.values = values;
    }
}
