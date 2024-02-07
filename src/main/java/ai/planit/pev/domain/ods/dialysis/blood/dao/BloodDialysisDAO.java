package ai.planit.pev.domain.ods.dialysis.blood.dao;

import ai.planit.pev.strategy.chart.object.dialysis.blood.BloodDialysisInfo;

public interface BloodDialysisDAO {
    BloodDialysisInfo getNrBloodDialysisInfo(String keyId);
}
