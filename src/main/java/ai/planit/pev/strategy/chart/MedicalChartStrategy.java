package ai.planit.pev.strategy.chart;

import ai.planit.pev.strategy.chart.constant.ChartClassType;
import ai.planit.pev.strategy.chart.object.common.ChartElement;
import ai.planit.pev.strategy.chart.object.medical.MedicalData;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class MedicalChartStrategy implements ChartStrategy {
    @Override
    public <T> List<ChartElement> getData(List<ChartElement> format, T source) {
        List<MedicalData> medicalDataList = (List<MedicalData>) source;

        List<ChartElement> data = new ArrayList<>();

        List<ChartElement> valueFormats = format
                .stream()
                .filter(f -> f.getClassType().equals(ChartClassType.VALUE))
                .collect(Collectors.toList());

        for (ChartElement valueFormat : valueFormats) {
            Optional<MedicalData> medicalData = medicalDataList
                    .stream()
                    .filter(m -> m.getSectionId() == valueFormat.getSectionId()
                            && m.getId().equals(valueFormat.getId())
                            && m.getParentId().equals(valueFormat.getParentId()))
                    .findAny();

            medicalData.ifPresentOrElse(m -> {
                valueFormat.setContent(m.getContent() == null ? "" : m.getContent());
                valueFormat.setDesc(m.getDesc() == null ? "" : m.getDesc());
            }, () -> {
                valueFormat.setContent("");
                valueFormat.setDesc("");
            });

            data.add(valueFormat);
        }

        return data;
    }
}
