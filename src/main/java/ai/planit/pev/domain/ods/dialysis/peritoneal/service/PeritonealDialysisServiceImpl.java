package ai.planit.pev.domain.ods.dialysis.peritoneal.service;

import ai.planit.pev.domain.ods.dialysis.peritoneal.dao.PeritonealDialysisDAO;
import ai.planit.pev.strategy.chart.object.dialysis.peritoneal.PeritonealDialysisData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PeritonealDialysisServiceImpl implements PeritonealDialysisService {
    private final PeritonealDialysisDAO peritonealDialysisDAO;

    @Override
    public PeritonealDialysisData getPeritonealDialysisData(String keyId) {
        PeritonealDialysisData peritonealDialysisData = new PeritonealDialysisData();

        peritonealDialysisData.setInfo(peritonealDialysisDAO.getNrPeritonealDialysisInfo(keyId));
        peritonealDialysisData.setObservationList(peritonealDialysisDAO.getNrPeritonealDialysisObservationList(keyId));

        return peritonealDialysisData;
    }
}
