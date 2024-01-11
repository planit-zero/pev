package ai.planit.pev.strategy.chart.object.specimen;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SpecimenData {
    private SpecimenInfo specimenInfo;
    private List<SpecimenResult> specimenResults;
}
