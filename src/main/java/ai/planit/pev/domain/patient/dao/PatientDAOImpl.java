package ai.planit.pev.domain.patient.dao;

import ai.planit.pev.domain.patient.dto.Patient;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PatientDAOImpl implements PatientDAO {
    private final SqlSessionTemplate sqlSessionTemplate;

    @Override
    public Patient getPatient(String ptNo) {
        return sqlSessionTemplate.selectOne("getPatientInfo", ptNo);
    }
}
