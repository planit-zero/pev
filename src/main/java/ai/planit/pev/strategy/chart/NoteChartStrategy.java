package ai.planit.pev.strategy.chart;

import ai.planit.pev.strategy.chart.constant.ChartClassType;
import ai.planit.pev.strategy.chart.object.common.ChartElement;
import ai.planit.pev.strategy.chart.object.note.NoteData;
import ai.planit.pev.strategy.chart.object.note.NoteGroup;
import ai.planit.pev.strategy.chart.object.note.NoteValue;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class NoteChartStrategy implements ChartStrategy {

    @Override
    public <T> List<ChartElement> getData(List<ChartElement> format, T source) {
        NoteData noteData = (NoteData) source;

        List<ChartElement> data = new ArrayList<>();

        List<ChartElement> valueFormats = format
                .stream()
                .filter(f -> f.getClassType().equals(ChartClassType.VALUE))
                .collect(Collectors.toList());

        for (ChartElement valueFormat : valueFormats) {
            if (valueFormat.getId().equals("nr-note-1-0-1")) {
                StringBuilder sb = new StringBuilder();

                for (NoteGroup noteGroup : noteData.getGroupList()) {
                    sb.append(String.format("%s [%s : %s]\r\n", noteGroup.getTime(), "작성자", noteGroup.getStfInfo()));

                    String valueStr = noteData.getValueList()
                            .stream()
                            .filter(v -> v.getTime().equals(noteGroup.getTime()))
                            .map(NoteValue::getValue)
                            .collect(Collectors.joining(" / "));

                    sb.append(String.format("%s\r\n\r\n", valueStr));
                }

                valueFormat.setContent(sb.toString());
                data.add(valueFormat);
            }
        }

        return data;
    }
}
