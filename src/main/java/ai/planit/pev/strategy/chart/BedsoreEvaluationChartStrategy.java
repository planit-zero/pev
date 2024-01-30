package ai.planit.pev.strategy.chart;

import ai.planit.pev.strategy.chart.constant.ChartClassType;
import ai.planit.pev.strategy.chart.object.bedsore.BedsoreEvaluationData;
import ai.planit.pev.strategy.chart.object.common.ChartElement;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BedsoreEvaluationChartStrategy implements ChartStrategy {

    @Override
    public <T> List<ChartElement> getData(List<ChartElement> format, T source) {
        BedsoreEvaluationData bedsoreEvaluationData = (BedsoreEvaluationData) source;

        List<ChartElement> data = new ArrayList<>();

        List<ChartElement> valueFormats = format
                .stream()
                .filter(f -> f.getClassType().equals(ChartClassType.VALUE))
                .collect(Collectors.toList());

        for (ChartElement valueFormat : valueFormats) {
            if (valueFormat.getId().equals("nr-bedsore-evaluation-1-0-1")) {
                valueFormat.setContent(String.join("\r\n", bedsoreEvaluationData.getContents()));
                data.add(valueFormat);
            }

            if (valueFormat.getId().equals("nr-bedsore-evaluation-2-0-1")) {
                valueFormat.setContent(bedsoreEvaluationData.getWriterNm());
                data.add(valueFormat);
            }
        }

        return data;
    }
}
