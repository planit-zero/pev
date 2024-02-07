package ai.planit.pev.domain.ods.dialysis.blood.service;

import ai.planit.pev.domain.ods.dialysis.blood.dao.BloodDialysisDAO;
import ai.planit.pev.strategy.chart.object.dialysis.blood.BloodDialysisData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BloodDialysisServiceImpl implements BloodDialysisService{
    private final BloodDialysisDAO bloodDialysisDAO;

    @Override
    public BloodDialysisData getBloodDialysisData(String keyId) {
        BloodDialysisData bloodDialysisData = new BloodDialysisData();

        bloodDialysisData.setInfo(bloodDialysisDAO.getNrBloodDialysisInfo(keyId));

        return bloodDialysisData;
    }
}
