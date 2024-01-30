package ai.planit.pev.domain.ods.discharge.service;

import ai.planit.pev.domain.ods.discharge.dao.DischargeDAO;
import ai.planit.pev.strategy.chart.object.discharge.DischargeData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DischargeServiceImpl implements DischargeService {
    private final DischargeDAO dischargeDAO;

    @Override
    public DischargeData getNrDischargeData(String keyId) {
        DischargeData dischargeData = new DischargeData();

        dischargeData.setContents(dischargeDAO.getNrDischargeContents(keyId));
        dischargeData.setWriterNm(dischargeDAO.getNrDischargeWriterText(keyId));

        return dischargeData;
    }
}
