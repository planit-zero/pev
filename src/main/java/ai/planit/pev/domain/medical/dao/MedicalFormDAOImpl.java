package ai.planit.pev.domain.medical.dao;

import ai.planit.pev.domain.medical.dto.MedicalFormData;
import ai.planit.pev.domain.record.dto.Record;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class MedicalFormDAOImpl implements MedicalFormDAO {
    private final SqlSessionTemplate sqlSessionTemplate;

    @Override
    public List<MedicalFormData> getMedicalFormData(Record.Response record) {
        return sqlSessionTemplate.selectList("getMedicalFormData", record);
    }
}
