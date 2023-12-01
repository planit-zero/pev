package ai.planit.pev.domain.ods.anesthesia.dao;

import ai.planit.pev.domain.ods.anesthesia.dto.AnesthesiaRecordData;

import java.util.List;

public interface AnesthesiaDAO {
    List<AnesthesiaRecordData> getAnesthesiaRecordDataList(String opExptRegId);
}
