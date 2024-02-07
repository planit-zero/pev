package ai.planit.pev.domain.ods.dialysis.peritoneal.dao;

import ai.planit.pev.strategy.chart.object.dialysis.peritoneal.PeritonealDialysisInfo;

public interface PeritonealDialysisDAO {
    PeritonealDialysisInfo getNrPeritonealDialysisInfo(String keyId);
}
