package ai.planit.pev.domain.ods.function.dao;

import ai.planit.pev.domain.ods.record.dto.Record;
import ai.planit.pev.strategy.chart.object.function.FunctionContent;

import java.util.List;

public interface FunctionDAO {
    List<Record.Response> getFunctionRecordList(String keyId);

    List<FunctionContent> getFunctionContentList(String keyId);
}
