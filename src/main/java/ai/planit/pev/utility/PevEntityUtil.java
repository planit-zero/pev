package ai.planit.pev.utility;

import ai.planit.pev.domain.ods.record.constant.RecordEntityPattern;
import ai.planit.pev.domain.ods.record.dto.RecordAttribute;
import ai.planit.pev.domain.ods.record.dto.RecordElement;
import ai.planit.pev.domain.ods.record.dto.RecordEntity;
import ai.planit.pev.domain.ods.record.dto.RecordValue;
import reactor.util.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class PevEntityUtil {
    /**
     * 별도의 Attribute 가 존재하지 않고 Entity-Value 가 1:1 관계인 Entity 생성
     *
     * @param element 요소의 속성 정보
     * @param entityText Entity 출력값
     * @param valueText Value 출력값
     * @return 문자열 형태의 단순 Entity
     */
    public static RecordEntity getSimpleTextEntity(@Nullable RecordElement element, String entityText, String valueText) {
        RecordEntity entity = new RecordEntity();

        entity.setType(RecordEntityPattern.TEXT.getType());

        // 좋은 방법이 있을 것 같은데...
        if (element != null) {
            entity.setDisplay(element.getDisplay());
            entity.setAlignment(element.getAlignment());
            entity.setTextDecoration(element.getTextDecoration());
        }

        entity.setText(entityText);
        entity.setValues(getSimpleTextValues(valueText));

        return entity;
    }

    public static RecordAttribute getSimpleTextAttribute(@Nullable RecordElement element, String attributeText, String valueText) {
        RecordAttribute attribute = new RecordAttribute();

        if (element != null) {
            attribute.setDisplay(element.getDisplay());
            attribute.setAlignment(element.getAlignment());
            attribute.setTextDecoration(element.getTextDecoration());
        }

        attribute.setText(attributeText);
        attribute.setValues(getSimpleTextValues(valueText));

        return attribute;
    }

    public static List<RecordValue> getSimpleTextValues(String valueText) {
        List<RecordValue> values = new ArrayList<>();

        RecordValue value = new RecordValue();
        value.setText(valueText);

        values.add(value);

        return values;
    }
}
