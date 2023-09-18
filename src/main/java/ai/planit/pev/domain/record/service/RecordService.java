package ai.planit.pev.domain.record.service;

import ai.planit.pev.domain.record.dto.Record;

import javax.servlet.http.HttpSession;
import java.util.List;

public interface RecordService {
    List<Record.Response> getRecordList(HttpSession session, Record.Request request);
}
