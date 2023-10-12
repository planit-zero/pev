package ai.planit.pev.domain.medical.dao;

import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MedicalFormHeaderSectionDAOImpl implements MedicalFormHeaderSectionDAO {
    private final SqlSessionTemplate sqlSessionTemplate;
}
