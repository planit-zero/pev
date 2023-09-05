package ai.planit.pev.domain.form.dao;

import ai.planit.pev.domain.form.dto.FormElement;
import ai.planit.pev.domain.form.dto.FormIdentifier;
import ai.planit.pev.domain.form.dto.FormInfoBasic;
import ai.planit.pev.domain.form.dto.FormSection;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class FormDAOImpl implements FormDAO {
    private final SqlSessionTemplate sqlSessionTemplate;

    public FormInfoBasic getFormBasicInfo(FormIdentifier identifier) {
        return sqlSessionTemplate.selectOne("getFormBasicInfo", identifier);
    }

    public List<FormSection> getFormSections(FormIdentifier identifier) {
        return sqlSessionTemplate.selectList("getFormSections", identifier);
    }

    public List<FormElement> getFormElements(FormIdentifier identifier) {
        return sqlSessionTemplate.selectList("getFormElements", identifier);
    }
}
