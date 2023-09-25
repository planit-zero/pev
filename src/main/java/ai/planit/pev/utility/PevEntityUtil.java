package ai.planit.pev.utility;

import ai.planit.pev.domain.record.dto.RecordEntity;
import ai.planit.pev.domain.record.dto.RecordValue;

import java.util.ArrayList;
import java.util.List;

public class PevEntityUtil {
    public static RecordEntity getSimpleTextEntity(boolean isInline, String entityText, String valueText) {
        RecordEntity entity = new RecordEntity();
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
