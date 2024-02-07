package ai.planit.pev.domain.ods.dialysis.peritoneal.service;

import ai.planit.pev.strategy.chart.object.dialysis.peritoneal.PeritonealDialysisData;

public interface PeritonealDialysisService {
    PeritonealDialysisData getPeritonealDialysisData(String keyId);
}
