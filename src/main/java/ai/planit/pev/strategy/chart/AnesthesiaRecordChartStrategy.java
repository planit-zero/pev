package ai.planit.pev.strategy.chart;

import ai.planit.pev.strategy.chart.constant.ChartClassType;
import ai.planit.pev.strategy.chart.object.anesthesia.AnesthesiaFormatValue;
import ai.planit.pev.strategy.chart.object.anesthesia.AnesthesiaRecordData;
import ai.planit.pev.strategy.chart.object.anesthesia.AnesthesiaRecordHistory;
import ai.planit.pev.strategy.chart.object.common.ChartElement;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class AnesthesiaRecordChartStrategy implements ChartStrategy {
    @Override
    public <T> List<ChartElement> getData(List<ChartElement> format, T source) {
        AnesthesiaRecordData anesthesiaRecordData = (AnesthesiaRecordData) source;

        List<ChartElement> data = new ArrayList<>();

        List<ChartElement> valueFormats = format
                .stream()
                .filter(f -> f.getClassType().equals(ChartClassType.VALUE))
                .collect(Collectors.toList());

        for (ChartElement valueFormat : valueFormats) {
            Optional<AnesthesiaFormatValue> anesthesiaFormatValue = anesthesiaRecordData.getFormatValues()
                    .stream()
                    .filter(v -> v.getMdfmCpemNo().equals(valueFormat.getMdfmCpemNo()))
                    .findAny();

            anesthesiaFormatValue.ifPresent(v -> valueFormat.setContent(v.getContent()));

            if (valueFormat.getId().equals("4-0-1")) {
                StringBuilder sb = new StringBuilder();

                for (AnesthesiaRecordHistory history : anesthesiaRecordData.getHistories()) {
                    sb.append(history.getInptHmi());
                    sb.append("\r\n");
                    sb.append(history.getInptValCnte());
                    sb.append("\r\n");
                    sb.append("\r\n");
                }

                valueFormat.setContent(sb.toString());
            }

            data.add(valueFormat);
        }

        return data;
    }
}
