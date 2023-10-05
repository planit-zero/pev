package ai.planit.pev.domain.hospital.dao;

import ai.planit.pev.domain.hospital.dto.Department;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class HospitalDAOImpl implements HospitalDAO {
    private final SqlSessionTemplate sqlSessionTemplate;

    @Override
    public List<Department> getDepartmentList() {
        return sqlSessionTemplate.selectList("getDepartmentList");
    }
}
