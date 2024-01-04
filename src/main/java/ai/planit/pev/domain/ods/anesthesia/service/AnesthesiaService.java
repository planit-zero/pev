package ai.planit.pev.domain.ods.anesthesia.service;

import ai.planit.pev.domain.ods.record.dto.Record;
import ai.planit.pev.strategy.chart.object.anesthesia.AnesthesiaRecordData;

public interface AnesthesiaService {
    Record.Response getAnesthesiaRecord(String opExptRegId);
    AnesthesiaRecordData getAnesthesiaRecordData(String mdfmClsCd, String opExptRegId);
}
