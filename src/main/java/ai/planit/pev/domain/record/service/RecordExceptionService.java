package ai.planit.pev.domain.record.service;

import ai.planit.pev.domain.record.dto.RecordExceptionRequestDTO;
import ai.planit.pev.domain.record.dto.SurgeryDefaultValueDTO;

import java.util.List;

public interface RecordExceptionService {
    List<SurgeryDefaultValueDTO> getSurgeryDefaultValueList(RecordExceptionRequestDTO recordExceptionRequestDTO);
}
