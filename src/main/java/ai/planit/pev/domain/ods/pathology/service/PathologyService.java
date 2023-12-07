package ai.planit.pev.domain.ods.pathology.service;

import ai.planit.pev.strategy.chart.object.pathology.PathologyData;

public interface PathologyService {
    PathologyData.Response getPathologyData(PathologyData.Request request);
}
