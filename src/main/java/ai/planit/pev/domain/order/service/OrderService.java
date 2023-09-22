package ai.planit.pev.domain.order.service;

import ai.planit.pev.domain.record.dto.Record;
import ai.planit.pev.domain.record.dto.RecordSheet;

import javax.servlet.http.HttpSession;

public interface OrderService {
    RecordSheet getRecordSheet(HttpSession session, Record.Response record);
}
