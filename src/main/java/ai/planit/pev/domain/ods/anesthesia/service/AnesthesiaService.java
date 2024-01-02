package ai.planit.pev.domain.ods.anesthesia.service;

import ai.planit.pev.strategy.chart.object.anesthesia.AnesthesiaRecordData;

public interface AnesthesiaService {
    AnesthesiaRecordData getAnesthesiaRecordData(String opExptRegId);
}
