package ai.planit.pev.strategy.chart;

import ai.planit.pev.strategy.chart.constant.ChartClassType;
import ai.planit.pev.strategy.chart.object.common.ChartElement;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class StatusChartStrategy implements ChartStrategy {

    @Override
    public <T> List<ChartElement> getData(List<ChartElement> format, T source) {
        List<ChartElement> statusData = (List<ChartElement>) source;

        List<ChartElement> data = new ArrayList<>();

        List<ChartElement> valueFormats = format
                .stream()
                .filter(f -> f.getClassType().equals(ChartClassType.VALUE))
                .collect(Collectors.toList());

        for (ChartElement valueFormat : valueFormats) {
            if (valueFormat.getId().equals("nr-status-1-0-1")) {

                List<ChartElement> commonValueList = statusData
                        .stream()
                        .filter(s -> s.getParentId().equals("nr-status-1"))
                        .collect(Collectors.toList());

                StringBuilder sb = new StringBuilder();

                for (ChartElement commonValue : commonValueList) {
                    sb.append(commonValue.getContent());
                    sb.append("\r\n");
                }

                valueFormat.setContent(sb.toString());
                data.add(valueFormat);
            }

            if (valueFormat.getId().equals("nr-status-2-0-1")) {

                List<ChartElement> commonValueList = statusData
                        .stream()
                        .filter(s -> s.getParentId().equals("nr-status-2"))
                        .collect(Collectors.toList());

                StringBuilder sb = new StringBuilder();

                for (ChartElement commonValue : commonValueList) {
                    sb.append(commonValue.getContent());
                    sb.append("\r\n");
                }

                valueFormat.setContent(sb.toString());
                data.add(valueFormat);
            }

            if (valueFormat.getId().equals("nr-status-3-0-1")) {

                List<ChartElement> commonValueList = statusData
                        .stream()
                        .filter(s -> s.getParentId().equals("nr-status-3"))
                        .collect(Collectors.toList());

                StringBuilder sb = new StringBuilder();

                for (ChartElement commonValue : commonValueList) {
                    sb.append(commonValue.getContent());
                    sb.append("\r\n");
                }

                valueFormat.setContent(sb.toString());
                data.add(valueFormat);
            }

        }

        return data;
    }
}
