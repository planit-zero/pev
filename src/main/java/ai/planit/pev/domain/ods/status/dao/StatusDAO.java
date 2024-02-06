package ai.planit.pev.domain.ods.status.dao;

import ai.planit.pev.strategy.chart.object.common.ChartElement;

import java.util.List;

public interface StatusDAO {
    List<ChartElement> getNrStatusValueList(String keyId);
}
