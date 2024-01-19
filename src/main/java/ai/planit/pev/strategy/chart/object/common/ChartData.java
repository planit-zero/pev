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
    private String pid;

    public ChartData(String pid, List<ChartElement> values) {
        this.pid = pid;
        this.values = values;
    }
}
