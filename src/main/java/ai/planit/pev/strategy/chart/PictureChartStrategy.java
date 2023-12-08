package ai.planit.pev.strategy.chart;


import ai.planit.pev.strategy.chart.constant.ChartClassType;
import ai.planit.pev.strategy.chart.object.common.ChartElement;
import ai.planit.pev.strategy.chart.object.picture.PictureData;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PictureChartStrategy implements ChartStrategy {
    @Override
    public <T> List<ChartElement> getData(List<ChartElement> format, T source) {
        PictureData.Response pictureData = (PictureData.Response) source;

        List<ChartElement> data = new ArrayList<>();

        List<ChartElement> valueFormats = format
                .stream()
                .filter(f -> f.getClassType().equals(ChartClassType.VALUE))
                .collect(Collectors.toList());

        for (ChartElement valueFormat : valueFormats) {
            if (valueFormat.getId().equals("picture-1-0-1")) {
                valueFormat.setContent(pictureData.getIptnCncsCnte());
                data.add(valueFormat);
            }

            if (valueFormat.getId().equals("picture-2-0-1")) {
                valueFormat.setContent(pictureData.getTh1IptnExpl());
                data.add(valueFormat);
            }

            if (valueFormat.getId().equals("picture-3-0-1")) {
                valueFormat.setContent(pictureData.getCopnCnte());
                data.add(valueFormat);
            }

            if (valueFormat.getId().equals("picture-4-0-1")) {
                String[] decoders = {
                        pictureData.getTh1IpdrStfNm(),
                        pictureData.getTh2IpdrStfNm(),
                        pictureData.getTh3IpdrStfNm(),
                        pictureData.getTh4IpdrStfNm(),
                        pictureData.getTh5IpdrStfNm(),
                        pictureData.getTh6IpdrStfNm()
                };

                String decoderStr = Stream.of(decoders)
                        .filter(d -> d != null && !d.isEmpty())
                        .collect(Collectors.joining(", "));

                valueFormat.setContent(decoderStr);
                data.add(valueFormat);
            }
        }

        return data;
    }
}
