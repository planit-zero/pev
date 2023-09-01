package ai.planit.pev.domain.record.dao;

import ai.planit.pev.domain.record.dto.RecordExceptionRequestDTO;
import ai.planit.pev.domain.record.dto.SurgeryDefaultValueDTO;

import java.util.List;

public interface RecordExceptionDAO {
    List<SurgeryDefaultValueDTO> getSurgeryDefaultValueList(RecordExceptionRequestDTO recordExceptionRequestDTO);
}
