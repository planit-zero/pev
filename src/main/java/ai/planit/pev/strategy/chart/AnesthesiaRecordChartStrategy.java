package ai.planit.pev.strategy.chart;

import ai.planit.pev.domain.ods.record.constant.RecordTarget;
import ai.planit.pev.strategy.chart.constant.ChartClassType;
import ai.planit.pev.strategy.chart.constant.ChartControlType;
import ai.planit.pev.strategy.chart.object.anesthesia.AnesthesiaFormatValue;
import ai.planit.pev.strategy.chart.object.anesthesia.AnesthesiaRecordData;
import ai.planit.pev.strategy.chart.object.anesthesia.AnesthesiaRecordHistory;
import ai.planit.pev.strategy.chart.object.common.ChartElement;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class AnesthesiaRecordChartStrategy implements ChartStrategy {
    private final String mdfmClsCd;

    public AnesthesiaRecordChartStrategy(String mdfmClsCd) {
        this.mdfmClsCd = mdfmClsCd;
    }

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

            anesthesiaFormatValue.ifPresent(v -> {
                if (valueFormat.getControlType().equals(ChartControlType.COMBO_BOX)) {
                    if (!valueFormat.getContent().equals(v.getContent())) {
                        valueFormat.setContent(null);
                    }

                    data.add(valueFormat);
                } else if (valueFormat.getControlType().equals(ChartControlType.RADIO_BUTTON) || valueFormat.getControlType().equals(ChartControlType.CHECK_BOX)) {
                    if (v.getContent().equals("1")) {
                        valueFormat.setDesc(valueFormat.getContent());
                        data.add(valueFormat);
                    }
                } else {
                    valueFormat.setContent(v.getContent());
                    data.add(valueFormat);
                }
            });

            if (mdfmClsCd.equals(RecordTarget.MEDICAL_ANESTHESIA.getType())) {
                if (valueFormat.getId().equals("anesthesia-record-history-1-0-1")) {
                    StringBuilder sb = new StringBuilder();

                    for (AnesthesiaRecordHistory history : anesthesiaRecordData.getHistories()) {
                        sb.append(history.getInptHmi());
                        sb.append("\r\n");
                        sb.append(history.getInptValCnte());
                        sb.append("\r\n");
                        sb.append("\r\n");
                    }

                    valueFormat.setContent(sb.toString());
                    data.add(valueFormat);
                }

                if (valueFormat.getId().equals("anesthesia-record-stf-nm-1-0-1")) {
                    valueFormat.setContent(anesthesiaRecordData.getSurgeryInfo().getStfNm());
                    data.add(valueFormat);
                }
            }

            if (valueFormat.getId().equals("anesthesia-record-op-nm-1-0-1")) {
                valueFormat.setContent(anesthesiaRecordData.getSurgeryInfo().getOpNm());
                data.add(valueFormat);
            }


        }

        return data;
    }
}
