package ai.planit.pev.domain.record.service;

import ai.planit.pev.domain.record.dao.RecordExceptionDAO;
import ai.planit.pev.domain.record.dto.RecordExceptionRequestDTO;
import ai.planit.pev.domain.record.dto.SurgeryDefaultValueDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RecordExceptionServiceImpl implements RecordExceptionService {
    private final RecordExceptionDAO recordExceptionDAO;

    @Override
    public List<SurgeryDefaultValueDTO> getSurgeryDefaultValueList(RecordExceptionRequestDTO recordExceptionRequestDTO) {
        return recordExceptionDAO.getSurgeryDefaultValueList(recordExceptionRequestDTO);
    }
}
