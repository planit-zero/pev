package ai.planit.pev.domain.ods.discharge.service;

import ai.planit.pev.strategy.chart.object.discharge.DischargeData;

public interface DischargeService {
    DischargeData getNrDischargeData(String keyId);
}
