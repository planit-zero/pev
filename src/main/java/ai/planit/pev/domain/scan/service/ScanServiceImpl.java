package ai.planit.pev.domain.scan.service;

import ai.planit.pev.core.exception.BaseException;
import ai.planit.pev.core.exception.ErrorType;
import ai.planit.pev.domain.record.constant.RecordElementClass;
import ai.planit.pev.domain.record.constant.RecordElementControl;
import ai.planit.pev.domain.record.dto.*;
import ai.planit.pev.utility.PevEntityUtil;
import ai.planit.pev.utility.PevStringUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ScanServiceImpl implements ScanService {
    public RecordSheet getRecordSheet(HttpSession session, Record.Response record) {
        String pid = (String) session.getAttribute("pev-pid");

        // 세션에 저장된 환자병록번호가 없을 경우 예외 처리한다.
        if (PevStringUtil.isStringEmpty(pid)) {
            throw new BaseException(ErrorType.PID_NOT_FOUND_IN_SESSION);
        }

        RecordSheet sheet = new RecordSheet();

        List<RecordSection> sections = new ArrayList<>();
        RecordSection section = new RecordSection();

        List<RecordEntity> entities = new ArrayList<>();

        RecordEntity entity = new RecordEntity();
        entity.setText("");
        entity.setTextDesc("");
        entity.setControlType(RecordElementControl.LABEL.getCode());
        entity.setClassType(RecordElementClass.ENTITY.getType());

        List<RecordAttribute> attributes = new ArrayList<>();
        entity.setAttributes(attributes);

        List<RecordValue> values = new ArrayList<>();

        RecordValue value = new RecordValue();
        value.setText(record.getExamKey());
        value.setTextDesc(String.format("%s%s", "http://hisimg.snuh.org/", record.getExamKey()));
        value.setControlType(RecordElementControl.IMAGE.getCode());
        value.setClassType(RecordElementClass.VALUE.getType());

        values.add(value);

        entity.setValues(values);
        entities.add(entity);
        section.setEntities(entities);
        sections.add(section);
        sheet.setSections(sections);

        return sheet;
    }
}
