package ai.planit.pev.domain.ods.status.service;

import ai.planit.pev.domain.ods.status.dao.StatusDAO;
import ai.planit.pev.strategy.chart.object.common.ChartElement;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StatusServiceImpl implements StatusService {
    private final StatusDAO statusDAO;

    @Override
    public List<ChartElement> getNrStatusValueList(String keyId) {
        return statusDAO.getNrStatusValueList(keyId);
    }
}
