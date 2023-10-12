package ai.planit.pev.domain.medical.service;

import ai.planit.pev.domain.record.dto.Record;
import ai.planit.pev.domain.record.dto.RecordSection;

public interface MedicalFormHeaderSectionService {
    RecordSection getRecordHeaderSection(Record.Response record);
}
