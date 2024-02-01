package ai.planit.pev.strategy.chart;

import ai.planit.pev.strategy.chart.constant.ChartClassType;
import ai.planit.pev.strategy.chart.constant.ChartControlType;
import ai.planit.pev.strategy.chart.object.common.ChartElement;
import ai.planit.pev.strategy.chart.object.function.FunctionContent;
import ai.planit.pev.strategy.chart.object.function.FunctionData;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class FunctionChartStrategy implements ChartStrategy {
    @Override
    public <T> List<ChartElement> getData(List<ChartElement> format, T source) {
        FunctionData functionData = (FunctionData) source;

        List<ChartElement> data = new ArrayList<>();

        List<ChartElement> valueFormats = format
                .stream()
                .filter(f -> f.getClassType().equals(ChartClassType.VALUE))
                .collect(Collectors.toList());

        for (ChartElement valueFormat : valueFormats) {
            valueFormat.setDesc(valueFormat.getContent());

            Optional<FunctionContent> functionContent = functionData.getContents()
                    .stream()
                    .filter(m -> m.getId().equals(valueFormat.getId()))
                    .findAny();

            functionContent.ifPresentOrElse(m -> {
                String content;

                if (valueFormat.getControlType().equals(ChartControlType.IMAGE)) {
                    content = String.format("%s%s", "http://hisimg.snuh.org/", m.getContent());
                } else {
                    content = m.getContent();
                }

                valueFormat.setContent(content == null ? "" : content);
            }, () -> valueFormat.setContent(""));

            data.add(valueFormat);
        }

        return data;
    }
}
