package ai.planit.pev.strategy.chart;

import ai.planit.pev.strategy.chart.constant.ChartClassType;
import ai.planit.pev.strategy.chart.object.common.ChartElement;
import ai.planit.pev.strategy.chart.object.dialysis.peritoneal.PeritonealDialysisData;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class PeritionealDialysisChartStrategy implements ChartStrategy {

    @Override
    public <T> List<ChartElement> getData(List<ChartElement> format, T source) {
        PeritonealDialysisData peritonealDialysisData = (PeritonealDialysisData) source;

        List<ChartElement> data = new ArrayList<>();

        List<ChartElement> valueFormats = format
                .stream()
                .filter(f -> f.getClassType().equals(ChartClassType.VALUE))
                .collect(Collectors.toList());

        for (ChartElement valueFormat : valueFormats) {
            if (valueFormat.getId().equals("nr-peritoneal-dialysis-1-0-1")) {

                StringBuilder sb = new StringBuilder();

                sb.append(String.format("%s : %s\r\n", "PD No.", StringUtils.defaultIfBlank(peritonealDialysisData.getInfo().getPdNo(), "")));
                sb.append(String.format("%s : %s\r\n", "Last wt.", StringUtils.defaultIfBlank(peritonealDialysisData.getInfo().getLastWeight(), "")));
                sb.append(String.format("%s : %s\r\n", "Today wt.", StringUtils.defaultIfBlank(peritonealDialysisData.getInfo().getTodayWeight(), "")));
                sb.append(String.format("%s : %s\r\n", "Wt 변화", StringUtils.defaultIfBlank(peritonealDialysisData.getInfo().getWeightChange(), "")));
                sb.append(String.format("%s : %s\r\n", "담당간호사", StringUtils.defaultIfBlank(peritonealDialysisData.getInfo().getNrStfNm(), "")));
                sb.append(String.format("%s : %s\r\n", "투석회사", StringUtils.defaultIfBlank(peritonealDialysisData.getInfo().getCompany(), "")));
                sb.append(String.format("%s : %s\r\n", "투석액종류", StringUtils.defaultIfBlank(peritonealDialysisData.getInfo().getFluidType(), "")));

                List<String> concentrationList = new ArrayList<>();

                concentrationList.add(peritonealDialysisData.getInfo().getFirstConcentration());
                concentrationList.add(peritonealDialysisData.getInfo().getSecondConcentration());
                concentrationList.add(peritonealDialysisData.getInfo().getThirdConcentration());
                concentrationList.add(peritonealDialysisData.getInfo().getFourthConcentration());

                String concentrationStr = concentrationList.stream().filter(Objects::nonNull).collect(Collectors.joining("/"));

                sb.append(String.format("%s : %s\r\n", "농도", StringUtils.defaultIfBlank(concentrationStr, "")));

                sb.append(String.format("%s : %s\r\n", "복막투석 스케쥴", StringUtils.defaultIfBlank(peritonealDialysisData.getInfo().getSchedule(), "")));
                sb.append(String.format("%s : %s\r\n", "복막투석 종류", StringUtils.defaultIfBlank(peritonealDialysisData.getInfo().getDialysisType(), "")));
                sb.append(String.format("%s : %s\r\n", "메시지", StringUtils.defaultIfBlank(peritonealDialysisData.getInfo().getMessage(), "")));
                sb.append(String.format("%s : %s\r\n", "Urine vol.", StringUtils.defaultIfBlank(peritonealDialysisData.getInfo().getUrineVolume(), "")));

                valueFormat.setContent(sb.toString());
                data.add(valueFormat);
            }
        }

        return data;
    }
}
