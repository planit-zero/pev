package ai.planit.pev.domain.record.dto;

import ai.planit.pev.domain.record.constant.RecordElementAlignment;
import ai.planit.pev.domain.record.constant.RecordElementDisplay;
import ai.planit.pev.domain.record.constant.RecordElementTextDecoration;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RecordElement {
    private String text;
    private String controlType;
    private String classType;
    private String display = RecordElementDisplay.BLOCK.getValue();
    private String alignment = RecordElementAlignment.LEFT.getValue();
    private String textDecoration = RecordElementTextDecoration.NORMAL.getValue();
}
