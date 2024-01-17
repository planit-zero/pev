package ai.planit.pev.domain.ods.inpatient.dao;

import ai.planit.pev.strategy.chart.object.common.ChartElement;

import java.util.List;

public interface InpatientDAO {
    List<ChartElement> getNrInpatientEntities(String keyId);
    List<ChartElement> getNrInpatientAttributes(String keyId);
    List<ChartElement> getNrInpatientValues(String keyId);
}
