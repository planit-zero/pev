package ai.planit.pev.domain.form.dao;

import ai.planit.pev.domain.form.dto.FormData;
import ai.planit.pev.domain.form.dto.FormStyleXML;
import ai.planit.pev.domain.record.dto.Record;

import java.util.List;

public interface FormDAO {
    List<FormData> getFormData(Record.Response record);

    FormStyleXML.Response getFormStyleXML(FormStyleXML.Request xmlRequest);
}
