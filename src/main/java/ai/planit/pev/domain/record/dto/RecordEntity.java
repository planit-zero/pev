package ai.planit.pev.domain.record.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RecordEntity extends RecordElement {
    private String type;
    private Boolean isInline;
    private List<RecordAttribute> attributes;
    private List<RecordValue> values;
}
