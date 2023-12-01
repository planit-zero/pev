package ai.planit.pev.domain.ods.function.service;

import ai.planit.pev.domain.ods.record.dto.Record;
import ai.planit.pev.domain.ods.record.dto.RecordSheet;

import javax.servlet.http.HttpSession;

public interface FunctionService {
    RecordSheet getRecordSheet(HttpSession session, Record.Response record);
}
