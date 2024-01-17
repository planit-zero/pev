package ai.planit.pev.domain.ods.inpatient.service;

import ai.planit.pev.domain.ods.record.dto.Record;
import ai.planit.pev.strategy.chart.object.common.ChartElement;

import java.util.List;

public interface InpatientService {
    List<ChartElement> getInpatientFormat(Record.Response record);
}
