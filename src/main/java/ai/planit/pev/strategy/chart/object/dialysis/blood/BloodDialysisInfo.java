package ai.planit.pev.strategy.chart.object.dialysis.blood;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BloodDialysisInfo {
    private String hdNo;
    private String machineNo;
    private String duration;
    private String dfr;
    private String patientType;
    private String currentPreWeight;
    private String currentPostWeight;
    private String currentWeightLost;
    private String uf;
    private String beforePostWeight;
    private String dryWeight;
    private String beforeWeightGain;
    private String tuf;
    private String primingFluid;
    private String fluidType;
    private String machineType;
    private String bloodVesselType;
    private String primingStfNm;
    private String startStfNm;
    private String assignStfNm;
    private String finishStfNm;
}
