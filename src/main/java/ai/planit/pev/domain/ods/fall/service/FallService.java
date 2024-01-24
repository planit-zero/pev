package ai.planit.pev.domain.ods.fall.service;

import ai.planit.pev.strategy.chart.object.fall.FallData;

public interface FallService {
    FallData getFallData(String keyId);
}
