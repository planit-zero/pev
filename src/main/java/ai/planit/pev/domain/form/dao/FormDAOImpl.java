package ai.planit.pev.domain.form.dao;

import ai.planit.pev.domain.form.dto.FormData;
import ai.planit.pev.domain.form.dto.FormStyleXML;
import ai.planit.pev.domain.record.dto.Record;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class FormDAOImpl implements FormDAO {
    private final SqlSessionTemplate sqlSessionTemplate;

    @Override
    public List<FormData> getFormData(Record.Response record) {
        return sqlSessionTemplate.selectList("getFormData", record);
    }

    @Override
    public FormStyleXML.Response getFormStyleXML(FormStyleXML.Request xmlRequest) {
        return sqlSessionTemplate.selectOne("getFormXMLData", xmlRequest);
    }
}
