import * as React from 'react';
import { IRecordAttribute } from '../../pev-interface/IRecord';
import { TRecordSection } from '../../pev-type/TRecordSection';
import { Typography } from '@mui/material';
import RecordValue from './RecordValue';
import { TRecordElementDisplay } from '../../pev-type/TRecordElement';

type RecordAttributeProps = {
    sectionType: string;
    attribute: IRecordAttribute;
};

const RecordAttribute = (props: RecordAttributeProps) => {
    return (
        <React.Fragment>
            <Typography
                sx={{
                    fontSize: 'h5.fontSize',
                    fontWeight: 'bold',
                    color: props.sectionType === TRecordSection.HEADER ? '#aa58d2' : 'inherit',
                    whiteSpace: 'pre-line',
                    wordBreak: 'break-all',
                    mr: props.attribute.display === TRecordElementDisplay.INLINE ? 1 : 0
                }}
                display={props.attribute.display}
            >
                {props.attribute.text}
            </Typography>
            {props.attribute.values &&
                props.attribute.values.map((value, idx) => {
                    return <RecordValue key={idx} sectionType={props.sectionType} display={props.attribute.display} value={value} />;
                })}
        </React.Fragment>
    );
};

export default RecordAttribute;
