package ai.planit.pev.strategy.chart;

import ai.planit.pev.strategy.chart.object.common.Chart;
import ai.planit.pev.strategy.chart.object.common.ChartElement;
import ai.planit.pev.utility.PevChartUtil;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter
@Setter
@NoArgsConstructor
public class ChartContext {
    private ChartStrategy chartStrategy;

    public Chart getChart(List<ChartElement> format, List<ChartElement> data) {
        List<ChartElement> elements = new ArrayList<>();

        for (ChartElement f : format) {
            Optional<ChartElement> value = data
                    .stream()
                    .filter(d -> d.getId().equals(f.getId()) && d.getParentId().equals(f.getParentId()))
                    .findFirst();

            value.ifPresentOrElse(elements::add, () -> elements.add(f));
        }

        Chart chart = new Chart();
        chart.setSections(PevChartUtil.getChartSections(elements));

        return chart;
    }
}
