package ai.planit.pev.domain.record.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RecordEntity extends RecordElement {
    private List<RecordAttribute> attributes;
    private List<RecordValue> values;
}
