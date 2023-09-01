import * as React from 'react';
import { IRecordItem } from '../../../pev-interface/IRecordDataR';
import { Typography } from '@mui/material';

type RecordFormBasicItemProps = {
    item: IRecordItem;
};

const Label = (item: IRecordItem) => {
    let sx = { fontSize: `h4.fontSize`, fontWeight: 'bold', color: '#4cbded' };
    if (item.archDepth !== 'NONE') sx = { fontSize: `h5.fontSize`, fontWeight: 'normal', color: '#4cbded' };
    return <Typography sx={sx}>{item.text}</Typography>;
};

const RichTextBox = (item: IRecordItem) => {
    return <Typography sx={{ fontSize: 'h5.fontSize', whiteSpace: 'pre-line', wordBreak: 'break-all' }}>{item.value}</Typography>;
};

const RecordFormBasicItem = (props: RecordFormBasicItemProps) => {
    if (props.item.type === 'Label') return Label(props.item);
    if (props.item.type === 'RichTextBox') return RichTextBox(props.item);
    return null;
};

export default RecordFormBasicItem;
