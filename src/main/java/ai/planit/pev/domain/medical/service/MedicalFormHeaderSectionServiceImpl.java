package ai.planit.pev.domain.medical.service;

import ai.planit.pev.domain.record.constant.RecordElementDisplay;
import ai.planit.pev.domain.record.constant.RecordTargetType;
import ai.planit.pev.domain.record.dto.*;
import ai.planit.pev.utility.PevEntityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MedicalFormHeaderSectionServiceImpl implements MedicalFormHeaderSectionService {

    @Override
    public RecordSection getRecordHeaderSection(Record.Response record) {
        if (record.getRecordType().equals(RecordTargetType.MEDICAL_RECORD.getCode())) {
            if (
                    record.getRecordDetailType().equals(RecordTargetType.MEDICAL_OUTPATIENT_FIRST.getCode())
                            || record.getRecordDetailType().equals(RecordTargetType.MEDICAL_OUTPATIENT_PROGRESS.getCode())
                            || record.getRecordDetailType().equals(RecordTargetType.MEDICAL_INPATIENT_FIRST.getCode())
                            || record.getRecordDetailType().equals(RecordTargetType.MEDICAL_INPATIENT_PROGRESS.getCode())
                            || record.getRecordDetailType().equals(RecordTargetType.MEDICAL_EMERGENCY.getCode())
                            || record.getRecordDetailType().equals(RecordTargetType.MEDICAL_SURGERY.getCode())
                            || record.getRecordDetailType().equals(RecordTargetType.MEDICAL_DISCHARGE.getCode())
                            || record.getRecordDetailType().equals(RecordTargetType.MEDICAL_REQUEST.getCode())
                            || record.getRecordDetailType().equals(RecordTargetType.MEDICAL_COVER.getCode())
            ) {
                return getRecordHeaderSectionWithItemNameAndDepartment(record);
            }

            if (
                    record.getRecordDetailType().equals(RecordTargetType.MEDICAL_ANESTHESIA.getCode())
                            || record.getRecordDetailType().equals(RecordTargetType.MEDICAL_BEFORE_ANESTHESIA.getCode())
                            || record.getRecordDetailType().equals(RecordTargetType.MEDICAL_DEPARTMENT.getCode())
            ) {
                return getRecordHeaderSectionWithItemName(record);
            }
        }

        return null;
    }

    private RecordSection getRecordHeaderSectionWithItemName(Record.Response record) {
        RecordSection section = new RecordSection();

        List<RecordEntity> entities = new ArrayList<>();

        RecordElement element = new RecordElement();
        element.setDisplay(RecordElementDisplay.INLINE.getValue());

        entities.add(PevEntityUtil.getSimpleTextEntity(element, String.format("%s (%s)", record.getItemNm(), record.getWritingDate()), null));
        section.setEntities(entities);

        return section;
    }

    private RecordSection getRecordHeaderSectionWithItemNameAndDepartment(Record.Response record) {
        RecordSection section = new RecordSection();

        List<RecordEntity> entities = new ArrayList<>();

        RecordElement element = new RecordElement();
        element.setDisplay(RecordElementDisplay.INLINE.getValue());

        RecordEntity entity = new RecordEntity();
        entity.setText(String.format("%s (%s)", record.getItemNm(), record.getWritingDate()));

        List<RecordAttribute> attributes = new ArrayList<>();

        attributes.add(PevEntityUtil.getSimpleTextAttribute(element, "작성과:", record.getWritingDeptNm()));
        attributes.add(PevEntityUtil.getSimpleTextAttribute(element, "수진과:", record.getPtMedDeptNm()));

        entity.setAttributes(attributes);
        entities.add(entity);
        section.setEntities(entities);

        return section;
    }
}
