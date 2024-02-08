package ai.planit.pev.domain.ods.dialysis.peritoneal.dao;

import ai.planit.pev.strategy.chart.object.dialysis.peritoneal.PeritonealDialysisInfo;
import ai.planit.pev.strategy.chart.object.dialysis.peritoneal.PeritonealDialysisObservation;

import java.util.List;

public interface PeritonealDialysisDAO {
    PeritonealDialysisInfo getNrPeritonealDialysisInfo(String keyId);
    List<PeritonealDialysisObservation> getNrPeritonealDialysisObservationList(String keyId);
}
