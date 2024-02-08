package ai.planit.pev.domain.ods.dialysis.blood.dao;

import ai.planit.pev.strategy.chart.object.dialysis.blood.BloodDialysisInfo;
import ai.planit.pev.strategy.chart.object.dialysis.blood.BloodDialysisObservation;

import java.util.List;

public interface BloodDialysisDAO {
    BloodDialysisInfo getNrBloodDialysisInfo(String keyId);
    List<BloodDialysisObservation> getNrBloodDialysisObservationList(String keyId);
}
