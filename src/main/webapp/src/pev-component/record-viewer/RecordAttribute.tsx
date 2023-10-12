import * as React from 'react';
import { IRecordAttribute } from '../../pev-interface/IRecord';
import { TRecordSection } from '../../pev-type/TRecordSection';
import { Box, Typography } from '@mui/material';
import RecordValue from './RecordValue';
import { TRecordElementDisplay } from '../../pev-type/TRecordElement';

type RecordAttributeProps = {
    sectionType: string;
    attribute: IRecordAttribute;
};

const RecordAttribute = (props: RecordAttributeProps) => {
    return (
        <Box sx={{ ml: 1 }}>
            <Typography
                sx={{
                    fontSize: 'h5.fontSize',
                    color: props.sectionType === TRecordSection.HEADER ? '#aa58d2' : '#409ac0',
                    whiteSpace: 'pre-line',
                    wordBreak: 'break-all',
                    mr: props.attribute.display === TRecordElementDisplay.INLINE ? 1 : 0
                }}
                display={props.attribute.display}
            >
                {props.attribute.text}
            </Typography>
            {props.attribute.attributes &&
                props.attribute.attributes.map((attribute, idx) => {
                    return <RecordAttribute key={idx} sectionType={props.sectionType} attribute={attribute} />;
                })}
            {props.attribute.values &&
                props.attribute.values.map((value, idx) => {
                    return <RecordValue key={idx} sectionType={props.sectionType} display={props.attribute.display} value={value} />;
                })}
        </Box>
    );
};

export default RecordAttribute;
