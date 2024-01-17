package ai.planit.pev.domain.ods.execute.service;

import ai.planit.pev.domain.ods.record.dto.Record;
import ai.planit.pev.strategy.chart.object.common.ChartElement;

import java.util.List;

public interface ExecuteService {
    List<ChartElement> getNrExecuteFormat(Record.Response record);
}
