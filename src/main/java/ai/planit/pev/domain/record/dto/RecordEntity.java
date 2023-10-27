package ai.planit.pev.domain.record.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class RecordEntity extends RecordElement {
    private String type;
    private List<RecordAttribute> attributes;
    private List<RecordValue> values;

    public RecordEntity(RecordElement element) {
        this.setText(element.getText());
        this.setTextDesc(element.getTextDesc());
        this.setControlType(element.getControlType());
        this.setClassType(element.getClassType());
        this.setDisplay(element.getDisplay());
        this.setAlignment(element.getAlignment());
        this.setTextDecoration(element.getTextDecoration());
        this.setFormStyleItem(element.getFormStyleItem());
    }
}
