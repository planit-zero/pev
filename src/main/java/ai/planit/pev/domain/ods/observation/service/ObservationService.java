package ai.planit.pev.domain.ods.observation.service;

import ai.planit.pev.domain.ods.record.dto.Record;
import ai.planit.pev.strategy.chart.object.observation.ObservationData;

import javax.servlet.http.HttpSession;

public interface ObservationService {
    ObservationData getObservationData(HttpSession session, Record.Response record);
}
