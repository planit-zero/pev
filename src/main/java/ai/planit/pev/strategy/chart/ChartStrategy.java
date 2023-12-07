package ai.planit.pev.strategy.chart;

import ai.planit.pev.strategy.chart.object.common.ChartElement;
import java.util.List;

public interface ChartStrategy {
    <T> List<ChartElement> getData(List<ChartElement> format, T source);
}
