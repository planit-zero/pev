type RecordType = {
    DR: string;
    OR: string;
    NR: string;
    EX: string;
    SC: string;
};

export const TRecord: RecordType = {
    DR: 'DR',
    OR: 'OR',
    NR: 'NR',
    EX: 'EX',
    SC: 'SC'
};

type RecordElementClassType = {
    LABEL: string;
    TEXT_BOX: string;
    RICH_TEXT_BOX: string;
    COMBO_BOX: string;
    CHECK_BOX: string;
    RADIO_BUTTON: string;
    IMAGE: string;
    NUMERIC_TEXT_BOX: string;
    DATE_TEXT_BOX: string;
    DATA_GRID: string;
};

export const TRecordElementClass: RecordElementClassType = {
    LABEL: 'Label', // 1
    TEXT_BOX: 'TextBox', // 2
    RICH_TEXT_BOX: 'RichTextBox', // 3
    COMBO_BOX: 'ComboBox', // 4
    CHECK_BOX: 'CheckBox', // 5
    RADIO_BUTTON: 'RadioButton', // 6
    IMAGE: 'Image', // 7
    NUMERIC_TEXT_BOX: 'NumericTextBox', // 8
    DATE_TEXT_BOX: 'DateTextBox', // 9
    DATA_GRID: 'DataGrid' // 12
};
