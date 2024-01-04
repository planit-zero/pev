package ai.planit.pev.strategy.chart.object.anesthesia;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AnesthesiaRecordData {
    private List<AnesthesiaRecordHistory> histories;
    private List<AnesthesiaFormatValue> formatValues;
}
