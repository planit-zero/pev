package ai.planit.pev.domain.ods.record.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RecordValue extends RecordElement {
    public RecordValue(RecordElement element) {
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
