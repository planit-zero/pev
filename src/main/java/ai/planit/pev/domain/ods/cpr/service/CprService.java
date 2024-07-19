package ai.planit.pev.domain.ods.cpr.service;

import ai.planit.pev.domain.ods.record.dto.Record;
import ai.planit.pev.strategy.chart.object.cpr.CprData;

import java.util.List;

public interface CprService {

    List<CprData> getCprData(Record.Response record);

}
