package ai.planit.pev.domain.ods.anesthesia.dao;

import ai.planit.pev.strategy.chart.object.anesthesia.AnesthesiaRecordHistory;

import java.util.List;

public interface AnesthesiaDAO {
    List<AnesthesiaRecordHistory> getAnesthesiaRecordHistories(String opExptRegId);
}
