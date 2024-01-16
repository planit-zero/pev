package ai.planit.pev.domain.ods.observation.dao;

import ai.planit.pev.strategy.chart.object.observation.ObservationContent;
import ai.planit.pev.strategy.chart.object.observation.ObservationRequest;

import java.util.List;

public interface ObservationDAO {
    List<ObservationContent> getObservationContents(ObservationRequest request);
}
