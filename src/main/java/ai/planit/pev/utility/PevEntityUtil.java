package ai.planit.pev.utility;

import ai.planit.pev.domain.record.constant.RecordEntityType;
import ai.planit.pev.domain.record.dto.RecordEntity;
import ai.planit.pev.domain.record.dto.RecordValue;

import java.util.ArrayList;
import java.util.List;

public class PevEntityUtil {
    /**
     * 별도의 Attribute 가 존재하지 않고 Entity-Value 가 1:1 관계인 Entity 생성
     *
     * @param isInline 줄바꿈 여부 (false -> 개행)
     * @param entityText Entity 출력값
     * @param valueText Value 출력값
     * @return 문자열 형태의 단순 Entity
     */
    public static RecordEntity getSimpleTextEntity(boolean isInline, String entityText, String valueText) {
        RecordEntity entity = new RecordEntity();
        entity.setType(RecordEntityType.TEXT.getType());
        entity.setInline(isInline);
        entity.setText(entityText);

        List<RecordValue> values = new ArrayList<>();

        RecordValue value = new RecordValue();
        value.setText(valueText);

        values.add(value);
        entity.setValues(values);

        return entity;
    }
}
