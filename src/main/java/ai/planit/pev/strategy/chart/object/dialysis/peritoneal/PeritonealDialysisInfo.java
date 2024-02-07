package ai.planit.pev.strategy.chart.object.dialysis.peritoneal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PeritonealDialysisInfo {
    private String pdNo;
    private String lastWeight;
    private String todayWeight;
    private String weightChange;
    private String nrStfNm;
    private String company;
    private String fluidType;
    private String firstConcentration;
    private String secondConcentration;
    private String thirdConcentration;
    private String fourthConcentration;
    private String schedule;
    private String dialysisType;
    private String message;
    private String urineVolume;
}
