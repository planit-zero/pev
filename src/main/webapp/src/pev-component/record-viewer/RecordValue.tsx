import * as React from 'react';
import { IRecordValue } from '../../pev-interface/IRecord';
import { TRecordSection } from '../../pev-type/TRecordSection';
import { Typography } from '@mui/material';

type RecordValueProps = {
    sectionType: string;
    display: string;
    value: IRecordValue;
};

const RecordValue = (props: RecordValueProps) => {
    return (
        <React.Fragment>
            {props.value.controlType !== '7' && (
                <Typography
                    sx={{
                        ml: 1,
                        fontSize: 'h5.fontSize',
                        color: props.sectionType === TRecordSection.HEADER ? '#aa58d2' : 'inherit',
                        whiteSpace: 'pre-line',
                        wordBreak: 'break-all'
                    }}
                    display={props.display}
                    className={'record-value'}
                >
                    {/*{props.value.text}*/}
                    <div style={{ display: props.display }} dangerouslySetInnerHTML={{ __html: props.value.text }} />
                </Typography>
            )}
            {props.value.controlType === '7' && <img src={`${props.value.text}`} alt={'이미지'} style={{ maxWidth: '100%' }} />}
        </React.Fragment>
    );
};

export default RecordValue;
