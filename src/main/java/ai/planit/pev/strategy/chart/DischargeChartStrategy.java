package ai.planit.pev.strategy.chart;

import ai.planit.pev.strategy.chart.constant.ChartClassType;
import ai.planit.pev.strategy.chart.object.common.ChartElement;
import ai.planit.pev.strategy.chart.object.discharge.DischargeContent;
import ai.planit.pev.strategy.chart.object.discharge.DischargeData;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DischargeChartStrategy implements ChartStrategy {

    @Override
    public <T> List<ChartElement> getData(List<ChartElement> format, T source) {
        DischargeData dischargeData = (DischargeData) source;

        List<ChartElement> data = new ArrayList<>();

        List<ChartElement> valueFormats = format
                .stream()
                .filter(f -> f.getClassType().equals(ChartClassType.VALUE))
                .collect(Collectors.toList());

        for (ChartElement valueFormat : valueFormats) {
            if (valueFormat.getId().equals("nr-discharge-1-0-1")) {
                List<String> titles = dischargeData.getContents()
                        .stream()
                        .map(DischargeContent::getTitle)
                        .distinct()
                        .collect(Collectors.toList());

                StringBuilder sb = new StringBuilder();

                for (String title : titles) {
                    sb.append(title);
                    sb.append("\r\n");

                    List<String> contentsByTitle = dischargeData.getContents()
                            .stream()
                            .filter(c -> c.getTitle().equals(title))
                            .map(DischargeContent::getContent)
                            .collect(Collectors.toList());

                    for (int i = 0; i < contentsByTitle.size(); i++) {
                        sb.append(contentsByTitle.get(i));
                        sb.append("\r\n");

                        if (i == contentsByTitle.size() - 1) sb.append("\r\n");
                    }
                }

                valueFormat.setContent(sb.toString());
                data.add(valueFormat);
            }

            if (valueFormat.getId().equals("nr-discharge-2-0-1")) {
                valueFormat.setContent(dischargeData.getWriterNm());
                data.add(valueFormat);
            }

        }

        return data;
    }
}
