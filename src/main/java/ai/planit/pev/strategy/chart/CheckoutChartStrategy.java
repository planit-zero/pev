package ai.planit.pev.strategy.chart;

import ai.planit.pev.strategy.chart.constant.ChartClassType;
import ai.planit.pev.strategy.chart.object.checkout.CheckoutContent;
import ai.planit.pev.strategy.chart.object.checkout.CheckoutData;
import ai.planit.pev.strategy.chart.object.common.ChartElement;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CheckoutChartStrategy implements ChartStrategy {

    @Override
    public <T> List<ChartElement> getData(List<ChartElement> format, T source) {
        CheckoutData checkoutData = (CheckoutData) source;

        List<ChartElement> data = new ArrayList<>();

        List<ChartElement> valueFormats = format
                .stream()
                .filter(f -> f.getClassType().equals(ChartClassType.VALUE))
                .collect(Collectors.toList());

        for (ChartElement valueFormat : valueFormats) {
            if (valueFormat.getId().equals("nr-checkout-1-0-1")) {
                List<String> titles = checkoutData.getContents()
                        .stream()
                        .map(CheckoutContent::getTitle)
                        .distinct()
                        .collect(Collectors.toList());

                StringBuilder sb = new StringBuilder();

                for (String title : titles) {
                    sb.append(title);
                    sb.append("\r\n");

                    List<String> contentsByTitle = checkoutData.getContents()
                            .stream()
                            .filter(c -> c.getTitle().equals(title))
                            .map(CheckoutContent::getContent)
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

            if (valueFormat.getId().equals("nr-checkout-2-0-1")) {
                valueFormat.setContent(checkoutData.getWriterNm());
                data.add(valueFormat);
            }

        }

        return data;
    }
}
