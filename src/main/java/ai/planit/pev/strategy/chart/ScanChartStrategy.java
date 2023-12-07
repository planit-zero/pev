package ai.planit.pev.strategy.chart;

import ai.planit.pev.domain.ods.record.dto.Record;
import ai.planit.pev.strategy.chart.constant.ChartClassType;
import ai.planit.pev.strategy.chart.object.common.ChartElement;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ScanChartStrategy implements ChartStrategy {

    @Override
    public <T> List<ChartElement> getData(List<ChartElement> format, T source) {
        Record.Response record = (Record.Response) source;

        List<ChartElement> data = new ArrayList<>();

        List<ChartElement> valueFormats = format
                .stream()
                .filter(f -> f.getClassType().equals(ChartClassType.VALUE))
                .collect(Collectors.toList());


        for (ChartElement valueFormat : valueFormats) {
            if (valueFormat.getId().equals("scan-1-0-1")) {
                valueFormat.setContent(String.format("%s%s", "http://hisimg.snuh.org/", record.getExamKey()));
                data.add(valueFormat);
            }
        }

        return data;
    }
}
