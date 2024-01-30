package ai.planit.pev.strategy.chart;

import ai.planit.pev.strategy.chart.constant.ChartClassType;
import ai.planit.pev.strategy.chart.object.common.ChartElement;
import ai.planit.pev.strategy.chart.object.transfer.TransferContent;
import ai.planit.pev.strategy.chart.object.transfer.TransferData;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TransferChartStrategy implements ChartStrategy {

    @Override
    public <T> List<ChartElement> getData(List<ChartElement> format, T source) {
        TransferData dischargeData = (TransferData) source;

        List<ChartElement> data = new ArrayList<>();

        List<ChartElement> valueFormats = format
                .stream()
                .filter(f -> f.getClassType().equals(ChartClassType.VALUE))
                .collect(Collectors.toList());

        for (ChartElement valueFormat : valueFormats) {
            if (valueFormat.getId().equals("nr-transfer-1-0-1")) {
                List<String> titles = dischargeData.getContents()
                        .stream()
                        .map(TransferContent::getTitle)
                        .distinct()
                        .collect(Collectors.toList());

                StringBuilder sb = new StringBuilder();

                for (String title : titles) {
                    sb.append(title);
                    sb.append("\r\n");

                    List<String> contentsByTitle = dischargeData.getContents()
                            .stream()
                            .filter(c -> c.getTitle().equals(title))
                            .map(TransferContent::getContent)
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

            if (valueFormat.getId().equals("nr-transfer-2-0-1")) {
                valueFormat.setContent(dischargeData.getWriterNm());
                data.add(valueFormat);
            }

        }

        return data;
    }
}
