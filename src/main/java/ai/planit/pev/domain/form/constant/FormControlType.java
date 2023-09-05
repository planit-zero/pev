package ai.planit.pev.domain.form.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum FormControlType {
    LABEL("1", "Label"),
    TEXT_BOX("2", "TextBox"),
    RICH_TEXT_BOX("3", "RichTextBox"),
    COMBO_BOX("4", "ComboBox"),
    CHECK_BOX("5", "CheckBox"),
    RADIO_BUTTON("6", "RadioButton"),
    IMAGE("7", "Image"),
    NUMERIC_TEXT_BOX("8", "NumericTextBox"),
    DATE_TEXT_BOX("9", "DateTextBox"),
    DATA_GRID("12", "DataGrid");

    private final String code;
    private final String text;
}
