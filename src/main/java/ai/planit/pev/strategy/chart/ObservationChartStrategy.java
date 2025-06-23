package ai.planit.pev.strategy.chart;

import ai.planit.pev.strategy.chart.constant.ChartClassType;
import ai.planit.pev.strategy.chart.object.common.ChartElement;
import ai.planit.pev.strategy.chart.object.observation.ObservationContent;
import ai.planit.pev.strategy.chart.object.observation.ObservationData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ObservationChartStrategy implements ChartStrategy {

    private final static List<String> DoubleValuesKeys = Arrays.asList("Muscle Power Rt/Lt arm", "Muscle Power Rt/Lt leg", "E(eye)/V(verbal)/M(motor)");

    @Override
    public <T> List<ChartElement> getData(List<ChartElement> format, T source) {
        ObservationData observationData = (ObservationData) source;

        List<ChartElement> data = new ArrayList<>();

        List<ChartElement> valueFormats = format
                .stream()
                .filter(f -> f.getClassType().equals(ChartClassType.VALUE))
                .collect(Collectors.toList());

        for (ChartElement valueFormat : valueFormats) {
            if (valueFormat.getId().equals("nr-observation-1-0-1")) {
                StringBuilder sb = new StringBuilder();

                List<String> headers = observationData.getContents()
                        .stream()
                        .map(ObservationContent::getTime)
                        .distinct()
                        .sorted()
                        .collect(Collectors.toList());

                headers.add(0, "구분");

                sb.append(String.join("|||", headers));
                sb.append(";");

                List<String> itemList = observationData.getContents()
                        .stream()
                        .map(ObservationContent::getItem)
                        .distinct()
                        .collect(Collectors.toList());

                for (int i = 0; i < itemList.size(); i++) {
                    String item = itemList.get(i);

                    List<String> rows = new ArrayList<>();
                    rows.add(item);

                    for (String header: headers) {
                        if (header.equals("구분")) continue;

                        Optional<ObservationContent> content = observationData.getContents()
                                .stream()
                                .filter(c -> c.getItem().equals(item) && c.getTime().equals(header))
                                        .collect(Collectors.collectingAndThen(
                                                Collectors.toList(),
                                                list -> {
                                                    if (list.isEmpty()) return Optional.empty();
                                                    ObservationContent base = list.get(0);

                                                    if (DoubleValuesKeys.contains(item)) {
                                                        String mergedValue = list.stream()
                                                                .map(ObservationContent::getValue)
                                                                .collect(Collectors.joining("/"));
                                                        return Optional.of(new ObservationContent(base.getItem(), base.getTime(), mergedValue));
                                                    } else {
                                                        return Optional.of(base);
                                                    }
                                                }
                                        ));

                        content.ifPresentOrElse(c -> rows.add(c.getValue()), () -> rows.add(""));
                    }

                    sb.append(String.join("|||", rows));
                    if (i < itemList.size() - 1) sb.append(";");
                }

                valueFormat.setContent(sb.toString());
                data.add(valueFormat);
            }
        }

        return data;
    }
}
