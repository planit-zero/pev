package ai.planit.pev.domain.ods.form.dao;

import ai.planit.pev.domain.ods.form.dto.FormData;
import ai.planit.pev.domain.ods.form.dto.FormStyleXML;
import ai.planit.pev.domain.ods.record.dto.Record;

import java.util.List;

public interface FormDAO {
    List<FormData> getFormData(Record.Response record);

    FormStyleXML.Response getFormStyleXML(FormStyleXML.Request xmlRequest);
}
