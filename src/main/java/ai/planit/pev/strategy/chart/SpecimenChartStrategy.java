package ai.planit.pev.strategy.chart;

import ai.planit.pev.strategy.chart.constant.ChartClassType;
import ai.planit.pev.strategy.chart.object.common.ChartElement;
import ai.planit.pev.strategy.chart.object.specimen.SpecimenData;
import ai.planit.pev.strategy.chart.object.specimen.SpecimenResult;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SpecimenChartStrategy implements ChartStrategy {

    @Override
    public <T> List<ChartElement> getData(List<ChartElement> format, T source) {
        SpecimenData specimenData = (SpecimenData) source;

        List<ChartElement> data = new ArrayList<>();

        List<ChartElement> valueFormats = format
                .stream()
                .filter(f -> f.getClassType().equals(ChartClassType.VALUE))
                .collect(Collectors.toList());

        for (ChartElement valueFormat : valueFormats) {
            // 검사명
            if (valueFormat.getId().equals("specimen-1-0-1")) {
                valueFormat.setContent(specimenData.getSpecimenInfo().getExmCtgNm());
                data.add(valueFormat);
            }

            // 검체명
            if (valueFormat.getId().equals("specimen-2-0-1")) {
                valueFormat.setContent(specimenData.getSpecimenInfo().getSpcmNm());
                data.add(valueFormat);
            }

            // 검사결과
            if (valueFormat.getId().equals("specimen-3-0-1")) {
                List<SpecimenResult> specimenResults = specimenData.getSpecimenResults();

                StringBuilder sb = new StringBuilder();

                sb.append("항목명|||검사결과|||참고치;");

                int index = 1;

                for (SpecimenResult result : specimenResults) {
                    sb.append(result.getResult());
                    if (index < specimenResults.size()) sb.append(";");
                    index++;
                }

                valueFormat.setContent(sb.toString());
                data.add(valueFormat);
            }

            // 보고자
            if (valueFormat.getId().equals("specimen-4-0-1")) {
                String content = specimenData.getSpecimenInfo().getItemCbVrfcIptnCnte();

                valueFormat.setContent(content == null ? "" : content);
                data.add(valueFormat);
            }
        }

        return data;
    }
}
