package ai.planit.pev.domain.form.dao;

import ai.planit.pev.domain.form.dto.FormFixedSectionItemsInSurgery;
import ai.planit.pev.domain.form.dto.FormIdentifier;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class FormFixedSectionDAOImpl implements FormFixedSectionDAO {
    private final SqlSessionTemplate sqlSessionTemplate;

    public List<FormFixedSectionItemsInSurgery> getFixedSectionItemsInSurgery(FormIdentifier identifier) {
        return sqlSessionTemplate.selectList("getFixedSectionItemsInSurgery", identifier);
    }
}
