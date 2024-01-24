package ai.planit.pev.domain.ods.fall.service;

import ai.planit.pev.domain.ods.fall.dao.FallDAO;
import ai.planit.pev.strategy.chart.object.fall.FallData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FallServiceImpl implements FallService {
    private final FallDAO fallDAO;

    @Override
    public FallData getFallData(String keyId) {
        FallData fallData = new FallData();

        fallData.setDetailTextList(fallDAO.getFallDetailTextList(keyId));
        fallData.setTotalText(fallDAO.getFallTotalText(keyId));
        fallData.setWriterNm(fallDAO.getFallWriterNm(keyId));

        return fallData;
    }
}
