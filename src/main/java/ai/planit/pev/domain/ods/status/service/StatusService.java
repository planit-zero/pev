package ai.planit.pev.domain.ods.status.service;

import ai.planit.pev.strategy.chart.object.common.ChartElement;

import java.util.List;

public interface StatusService {
    List<ChartElement> getNrStatusValueList(String keyId);
}
