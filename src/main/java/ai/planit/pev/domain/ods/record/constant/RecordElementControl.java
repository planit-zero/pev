package ai.planit.pev.domain.ods.record.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RecordElementControl {
    LABEL("1", "Label"),
    TEXT_BOX("2", "TextBox"),
    RICH_TEXT_BOX("3", "RichTextBox"),
    COMBO_BOX("4", "ComboBox"),
    CHECK_BOX("5", "CheckBox"),
    RADIO_BUTTON("6", "RadioButton"),
    IMAGE("7", "Image"),
    NUMERIC_TEXT_BOX("8", "NumericTextBox"),
    DATE_TEXT_BOX("9", "DateTextBox"),
    DATA_GRID("12", "DataGrid"),

    ;

    private final String code;
    private final String desc;
}
