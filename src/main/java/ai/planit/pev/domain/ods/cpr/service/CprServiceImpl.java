package ai.planit.pev.domain.ods.cpr.service;

import ai.planit.pev.domain.ods.cpr.dao.CprDAO;
import ai.planit.pev.domain.ods.record.dto.Record;
import ai.planit.pev.strategy.chart.object.cpr.CprData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CprServiceImpl implements CprService {

    private final CprDAO cprDAO;

    @Override
    public List<CprData> getCprData(Record.Response record) {
        return cprDAO.getCprData(record);
    }

}
