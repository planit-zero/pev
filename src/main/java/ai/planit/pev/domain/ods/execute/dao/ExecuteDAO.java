package ai.planit.pev.domain.ods.execute.dao;

import ai.planit.pev.strategy.chart.object.common.ChartElement;

import java.util.List;

public interface ExecuteDAO {
    List<ChartElement> getNrExecuteAttributes(String keyId);
    List<ChartElement> getNrExecuteValues(String keyId);
}
