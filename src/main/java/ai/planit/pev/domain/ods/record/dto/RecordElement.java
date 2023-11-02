package ai.planit.pev.domain.ods.record.dto;

import ai.planit.pev.domain.ods.form.dto.FormStyleItem;
import ai.planit.pev.domain.ods.record.constant.RecordElementAlignment;
import ai.planit.pev.domain.ods.record.constant.RecordElementControl;
import ai.planit.pev.domain.ods.record.constant.RecordElementDisplay;
import ai.planit.pev.domain.ods.record.constant.RecordElementTextDecoration;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RecordElement {
    private String text;
    private String textDesc;
    private String controlType = RecordElementControl.LABEL.getCode();
    private String classType;
    private String display = RecordElementDisplay.BLOCK.getValue();
    private String alignment = RecordElementAlignment.LEFT.getValue();
    private String textDecoration = RecordElementTextDecoration.NORMAL.getValue();
    private FormStyleItem formStyleItem;
}
