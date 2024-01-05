package ai.planit.pev.domain.ods.anesthesia.dao;

import ai.planit.pev.domain.ods.record.dto.Record;
import ai.planit.pev.strategy.chart.object.anesthesia.AnesthesiaFormatRequest;
import ai.planit.pev.strategy.chart.object.anesthesia.AnesthesiaFormatValue;
import ai.planit.pev.strategy.chart.object.anesthesia.AnesthesiaRecordHistory;
import ai.planit.pev.strategy.chart.object.anesthesia.AnesthesiaSurgeryInfo;

import java.util.List;

public interface AnesthesiaDAO {
    Record.Response getAnesthesiaRecord(AnesthesiaFormatRequest request);
    List<AnesthesiaRecordHistory> getAnesthesiaRecordHistories(String opExptRegId);
    List<AnesthesiaFormatValue> getAnesthesiaFormatValue(AnesthesiaFormatRequest request);
    AnesthesiaSurgeryInfo getAnesthesiaSurgeryInfo(String opExptRegId);
}
