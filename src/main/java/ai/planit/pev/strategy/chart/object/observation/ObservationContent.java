package ai.planit.pev.strategy.chart.object.observation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ObservationContent {
    private String time;
    private String item;
    private String value;
}
