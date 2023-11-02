package ai.planit.pev.domain.ods.form.service;

import ai.planit.pev.domain.ods.record.dto.Record;
import ai.planit.pev.domain.ods.record.dto.RecordSheet;

import javax.servlet.http.HttpSession;

public interface FormService {
    RecordSheet getRecordSheet(HttpSession session, Record.Response record);
}
