package ai.planit.pev.domain.ods.anesthesia.service;

import ai.planit.pev.domain.ods.anesthesia.dao.AnesthesiaDAO;
import ai.planit.pev.domain.ods.record.dto.Record;
import ai.planit.pev.strategy.chart.object.anesthesia.AnesthesiaFormatRequest;
import ai.planit.pev.strategy.chart.object.anesthesia.AnesthesiaRecordData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AnesthesiaServiceImpl implements AnesthesiaService {
    private final AnesthesiaDAO anesthesiaDAO;

    @Override
    public Record.Response getAnesthesiaRecord(String opExptRegId) {
        return anesthesiaDAO.getAnesthesiaRecord(opExptRegId);
    }

    @Override
    public AnesthesiaRecordData getAnesthesiaRecordData(String mdfmClsCd, String opExptRegId) {
        AnesthesiaRecordData anesthesiaRecordData = new AnesthesiaRecordData();

        anesthesiaRecordData.setHistories(anesthesiaDAO.getAnesthesiaRecordHistories(opExptRegId));

        AnesthesiaFormatRequest request = new AnesthesiaFormatRequest();
        request.setMdfmClsCd(mdfmClsCd);
        request.setOpExptRegId(opExptRegId);

        anesthesiaRecordData.setFormatValues(anesthesiaDAO.getAnesthesiaFormatValue(request));

        return anesthesiaRecordData;
    }
}
