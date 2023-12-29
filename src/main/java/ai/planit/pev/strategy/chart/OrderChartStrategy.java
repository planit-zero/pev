package ai.planit.pev.strategy.chart;

import ai.planit.pev.strategy.chart.constant.ChartClassType;
import ai.planit.pev.strategy.chart.object.common.ChartElement;
import ai.planit.pev.strategy.chart.object.order.OrderContent;
import ai.planit.pev.strategy.chart.object.order.OrderData;
import ai.planit.pev.strategy.chart.object.order.OrderSection;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class OrderChartStrategy implements ChartStrategy {

    @Override
    public <T> List<ChartElement> getData(List<ChartElement> format, T source) {
        OrderData orderData = (OrderData) source;

        List<ChartElement> data = new ArrayList<>();

        List<ChartElement> valueFormats = format
                .stream()
                .filter(f -> f.getClassType().equals(ChartClassType.VALUE))
                .collect(Collectors.toList());

        for (ChartElement valueFormat : valueFormats) {
            if (valueFormat.getId().equals("0-0-1")) {

                StringBuilder sb = new StringBuilder();

                for (OrderSection.Response section: orderData.getSections()) {
                    sb.append("------------------------------");
                    sb.append("------------------------------");
                    sb.append("------------------------------");
                    sb.append("------------------------------");
                    sb.append("------------------------------");
                    sb.append("\r\n");
                    sb.append(String.format("%s >", section.getOdaplPopNm()));
                    sb.append("\r\n");
                    sb.append("\r\n");

                    for (OrderContent content : section.getContents()) {
                        sb.append(content.getOrdNm());
                        sb.append("\r\n");
                    }

                    sb.append("\r\n");
                    sb.append(String.format("작성자: %s", section.getFsrStfNm()));
                    sb.append("\r\n");
                    sb.append("------------------------------");
                    sb.append("------------------------------");
                    sb.append("------------------------------");
                    sb.append("------------------------------");
                    sb.append("------------------------------");
                    sb.append("\r\n");
                    sb.append("\r\n");
                }

                valueFormat.setContent(sb.toString());
                data.add(valueFormat);
            }
        }

        return data;
    }
}
