package ai.planit.pev.domain.ods.specimen.service;

import ai.planit.pev.domain.ods.record.dto.Record;
import ai.planit.pev.strategy.chart.object.specimen.SpecimenData;

import javax.servlet.http.HttpSession;

public interface SpecimenService {
    SpecimenData getSpecimenData(HttpSession session, Record.Response record);
}
