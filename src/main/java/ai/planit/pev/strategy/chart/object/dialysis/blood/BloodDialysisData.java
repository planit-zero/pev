package ai.planit.pev.strategy.chart.object.dialysis.blood;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BloodDialysisData {
    BloodDialysisInfo info;
    List<BloodDialysisObservation> observationList;
}
