package ai.planit.pev.domain.ods.function.service;

import ai.planit.pev.domain.ods.function.dao.FunctionDAO;
import ai.planit.pev.domain.ods.record.dto.Record;
import ai.planit.pev.strategy.chart.object.function.FunctionData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FunctionServiceImpl implements FunctionService {
    private final FunctionDAO functionDAO;

    @Override
    public List<Record.Response> getFunctionRecordList(String keyId) {
        return functionDAO.getFunctionRecordList(keyId);
    }

    @Override
    public FunctionData getFunctionData(String keyId) {
        FunctionData functionData = new FunctionData();
        functionData.setContents(functionDAO.getFunctionContentList(keyId));

        return functionData;
    }
}
