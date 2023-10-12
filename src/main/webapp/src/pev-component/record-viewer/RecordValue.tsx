import * as React from 'react';
import { IRecordValue } from '../../pev-interface/IRecord';
import { TRecordSection } from '../../pev-type/TRecordSection';
import { Box, Typography } from '@mui/material';

type RecordValueProps = {
    sectionType: string;
    display: string;
    value: IRecordValue;
};

const RecordValue = (props: RecordValueProps) => {
    return (
        <Box sx={{ ml: 1 }}>
            {props.value.controlType !== '7' && (
                <Typography
                    sx={{
                        fontSize: 'h5.fontSize',
                        color: props.sectionType === TRecordSection.HEADER ? '#aa58d2' : 'inherit',
                        whiteSpace: 'pre-line',
                        wordBreak: 'break-all'
                    }}
                    display={props.display}
                >
                    {props.value.text}
                </Typography>
            )}
            {props.value.controlType === '7' && (
                <img src={`http://hisimg.snuh.org/${props.value.text}`} alt={'이미지'} style={{ maxWidth: '100%' }} />
            )}
        </Box>
    );
};

export default RecordValue;
