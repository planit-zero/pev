package ai.planit.pev.domain.ods.bedsore.service;

import ai.planit.pev.strategy.chart.object.common.ChartElement;

import java.util.List;

public interface BedsoreService {
    List<ChartElement> getBedsoreFormat(String keyId);
}
