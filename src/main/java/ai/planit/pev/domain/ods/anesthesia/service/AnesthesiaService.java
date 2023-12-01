package ai.planit.pev.domain.ods.anesthesia.service;

import ai.planit.pev.domain.ods.record.dto.Record;
import ai.planit.pev.domain.ods.record.dto.RecordSheet;

import javax.servlet.http.HttpSession;

public interface AnesthesiaService {
    RecordSheet getRecordSheet(HttpSession session, Record.Response record);
}
