package ai.planit.pev.domain.ods.function.service;

import ai.planit.pev.domain.ods.record.dto.Record;
import ai.planit.pev.strategy.chart.object.function.FunctionData;

import java.util.List;

public interface FunctionService {
    List<Record.Response> getFunctionRecordList(String keyId);

    FunctionData getFunctionData(String keyId);
}
