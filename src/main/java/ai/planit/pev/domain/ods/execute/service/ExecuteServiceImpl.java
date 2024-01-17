package ai.planit.pev.domain.ods.execute.service;

import ai.planit.pev.domain.ods.execute.dao.ExecuteDAO;
import ai.planit.pev.domain.ods.record.dto.Record;
import ai.planit.pev.strategy.chart.constant.ChartClassType;
import ai.planit.pev.strategy.chart.constant.ChartControlType;
import ai.planit.pev.strategy.chart.object.common.ChartElement;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExecuteServiceImpl implements ExecuteService {
    private final ExecuteDAO executeDAO;

    @Override
    public List<ChartElement> getNrExecuteFormat(Record.Response record) {
        List<ChartElement> format = new ArrayList<>();

        ChartElement entity = new ChartElement();

        entity.setSectionId(1);
        entity.setId("nr-execute-1");
        entity.setParentId("-1000");
        entity.setMdfmCpemNo("nr-execute-1");
        entity.setClassType(ChartClassType.ENTITY);
        entity.setControlType(ChartControlType.LABEL);
        entity.setMaskingType(null);
        entity.setContent("");
        entity.setDesc(null);
        entity.setStyle(null);

        format.add(entity);
        format.addAll(executeDAO.getNrExecuteAttributes(record.getKeyId()));
        format.addAll(executeDAO.getNrExecuteValues(record.getKeyId()));

        return format;
    }
}
