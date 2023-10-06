import * as React from 'react';
import { IRecordValue } from '../../pev-interface/IRecord';
import { TRecordSection } from '../../pev-type/TRecordSection';
import { Typography } from '@mui/material';

type RecordValueProps = {
    sectionType: string;
    isInline: boolean;
    value: IRecordValue;
};

const RecordValue = (props: RecordValueProps) => {
    return (
        <Typography
            sx={{
                fontSize: 'h5.fontSize',
                color: props.sectionType === TRecordSection.HEADER ? '#aa58d2' : 'inherit',
                whiteSpace: 'pre-line',
                wordBreak: 'break-all'
            }}
            display={props.isInline ? 'inline' : 'block'}
        >
            {props.value.text}
        </Typography>
    );
};

export default RecordValue;
