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
    private boolean withOrigin; // 신규 추가

    public ChartData(String pid, List<ChartElement> values, boolean withOrigin) {
        this.pid = pid;
        this.values = values;
        this.withOrigin = withOrigin;
    }
}
