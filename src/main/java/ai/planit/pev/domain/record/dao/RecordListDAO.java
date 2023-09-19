package ai.planit.pev.domain.record.dao;

import ai.planit.pev.domain.record.dto.Record;

import java.util.List;

public interface RecordListDAO {
    List<Record.Response> getMedicalRecordList(Record.Request request);
    List<Record.Response> getSurgeryRecordList(Record.Request request);
    List<Record.Response> getDischargeRecordList(Record.Request request);
    List<Record.Response> getRequestRecordList(Record.Request request);
    List<Record.Response> getAnesthesiaRecordList(Record.Request request);
//    List<Record.Response> getOrderRecordList(Record.Request request);
}
