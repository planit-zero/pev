package ai.planit.pev.strategy.chart;

import ai.planit.pev.strategy.chart.constant.ChartClassType;
import ai.planit.pev.strategy.chart.object.common.ChartElement;
import ai.planit.pev.strategy.chart.object.fall.FallData;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FallChartStrategy implements ChartStrategy {

    @Override
    public <T> List<ChartElement> getData(List<ChartElement> format, T source) {
        FallData fallData = (FallData) source;

        List<ChartElement> data = new ArrayList<>();

        List<ChartElement> valueFormats = format
                .stream()
                .filter(f -> f.getClassType().equals(ChartClassType.VALUE))
                .collect(Collectors.toList());

        for (ChartElement valueFormat : valueFormats) {
            if (valueFormat.getId().equals("nr-fall-1-0-1")) {
                StringBuilder sb = new StringBuilder();

                for (String detailText : fallData.getDetailTextList()) {
                    sb.append(detailText);
                    sb.append("\r\n");
                }

                sb.append(fallData.getTotalText());

                valueFormat.setContent(sb.toString());
                data.add(valueFormat);
            }

            if (valueFormat.getId().equals("nr-fall-2-0-1")) {
                valueFormat.setContent(fallData.getWriterNm());
                data.add(valueFormat);
            }
        }

        return data;
    }
}
