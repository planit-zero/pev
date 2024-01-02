package ai.planit.pev.domain.ods.anesthesia.service;

import ai.planit.pev.domain.ods.anesthesia.dao.AnesthesiaDAO;
import ai.planit.pev.strategy.chart.object.anesthesia.AnesthesiaRecordData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AnesthesiaServiceImpl implements AnesthesiaService {
    private final AnesthesiaDAO anesthesiaDAO;

    @Override
    public AnesthesiaRecordData getAnesthesiaRecordData(String opExptRegId) {
        AnesthesiaRecordData anesthesiaRecordData = new AnesthesiaRecordData();

        anesthesiaRecordData.setHistories(anesthesiaDAO.getAnesthesiaRecordHistories(opExptRegId));

        return anesthesiaRecordData;
    }
}
