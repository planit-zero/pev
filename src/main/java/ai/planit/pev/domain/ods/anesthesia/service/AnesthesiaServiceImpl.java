package ai.planit.pev.domain.ods.anesthesia.service;

import ai.planit.pev.core.exception.BaseException;
import ai.planit.pev.core.exception.ErrorType;
import ai.planit.pev.domain.ods.anesthesia.dao.AnesthesiaDAO;
import ai.planit.pev.domain.ods.anesthesia.dto.AnesthesiaRecordData;
import ai.planit.pev.domain.ods.record.constant.RecordElementClass;
import ai.planit.pev.domain.ods.record.constant.RecordElementControl;
import ai.planit.pev.domain.ods.record.dto.*;
import ai.planit.pev.utility.PevStringUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AnesthesiaServiceImpl implements AnesthesiaService {
    private final AnesthesiaDAO anesthesiaDAO;

    @Override
    public RecordSheet getRecordSheet(HttpSession session, Record.Response record) {
        String pid = (String) session.getAttribute("pev-pid");

        // 세션에 저장된 환자병록번호가 없을 경우 예외 처리한다.
        if (PevStringUtil.isStringEmpty(pid)) {
            throw new BaseException(ErrorType.PID_NOT_FOUND_IN_SESSION);
        }

        RecordSheet recordSheet = new RecordSheet();

        recordSheet.setHeaderSection(getRecordHeaderSection(record));
        recordSheet.setSections(getRecordSections(record));

        return recordSheet;
    }

    private RecordSection getRecordHeaderSection(Record.Response record) {
        RecordSection section = new RecordSection();

        List<RecordEntity> entities = new ArrayList<>();

        RecordEntity entity = new RecordEntity();
        entity.setText(String.format("%s (%s)", record.getItemNm(), record.getWritingDate()));

        List<RecordAttribute> attributes = new ArrayList<>();

        entity.setAttributes(attributes);
        entities.add(entity);

        section.setEntities(entities);

        return section;
    }

    private List<RecordSection> getRecordSections(Record.Response record) {
        List<RecordSection> sections = new ArrayList<>();

        sections.add(getRecordSection(record));

        return sections;
    }

    private RecordSection getRecordSection(Record.Response record) {
        RecordSection section = new RecordSection();

        List<AnesthesiaRecordData> anesthesiaRecordDataList = anesthesiaDAO.getAnesthesiaRecordDataList(record.getOpExptRegId());

        List<RecordEntity> entities = new ArrayList<>();

        RecordEntity entity = new RecordEntity();
        entity.setText("기록");

        List<RecordAttribute> attributes = new ArrayList<>();

        for (AnesthesiaRecordData anesthesiaRecordData : anesthesiaRecordDataList) {
            RecordAttribute attribute = new RecordAttribute();
            attribute.setText(anesthesiaRecordData.getInptHmi());

            List<RecordValue> values = new ArrayList<>();

            RecordValue contentValue = new RecordValue();
            contentValue.setClassType(RecordElementClass.VALUE.getType());
            contentValue.setControlType(RecordElementControl.RICH_TEXT_BOX.getCode());
            contentValue.setText(anesthesiaRecordData.getInptValCnte());

            values.add(contentValue);

            attribute.setValues(values);
            attributes.add(attribute);
        }

        entity.setAttributes(attributes);

        entities.add(entity);
        section.setEntities(entities);

        return section;
    }


}
