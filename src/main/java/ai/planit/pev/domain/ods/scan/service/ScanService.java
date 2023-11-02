package ai.planit.pev.domain.ods.scan.service;

import ai.planit.pev.domain.ods.record.dto.Record;
import ai.planit.pev.domain.ods.record.dto.RecordSheet;

import javax.servlet.http.HttpSession;

public interface ScanService {
    RecordSheet getRecordSheet(HttpSession session, Record.Response record);
}
