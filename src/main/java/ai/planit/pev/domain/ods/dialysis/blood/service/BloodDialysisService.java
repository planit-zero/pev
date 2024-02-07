package ai.planit.pev.domain.ods.dialysis.blood.service;

import ai.planit.pev.strategy.chart.object.dialysis.blood.BloodDialysisData;

public interface BloodDialysisService {
    BloodDialysisData getBloodDialysisData(String keyId);
}
