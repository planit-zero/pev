package ai.planit.pev.domain.ods.patient.dao;

import ai.planit.pev.domain.ods.patient.dto.Patient;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PatientDAOImpl implements PatientDAO {
    private final SqlSessionTemplate sqlSessionTemplate;

    public Patient getPatient(String pid) {
        return sqlSessionTemplate.selectOne("getPatient", pid);
    }
}
