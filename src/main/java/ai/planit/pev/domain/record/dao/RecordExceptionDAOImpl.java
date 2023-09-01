package ai.planit.pev.domain.record.dao;

import ai.planit.pev.domain.record.dto.RecordExceptionRequestDTO;
import ai.planit.pev.domain.record.dto.SurgeryDefaultValueDTO;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class RecordExceptionDAOImpl implements RecordExceptionDAO {
    private final SqlSessionTemplate sqlSessionTemplate;

    @Override
    public List<SurgeryDefaultValueDTO> getSurgeryDefaultValueList(RecordExceptionRequestDTO recordExceptionRequestDTO) {
        return sqlSessionTemplate.selectList("getSurgeryDefaultValueList", recordExceptionRequestDTO);
    }
}
