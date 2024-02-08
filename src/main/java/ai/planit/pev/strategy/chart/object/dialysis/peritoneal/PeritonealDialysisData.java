package ai.planit.pev.strategy.chart.object.dialysis.peritoneal;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PeritonealDialysisData {
    private PeritonealDialysisInfo info;
    private List<PeritonealDialysisObservation> observationList;
}
