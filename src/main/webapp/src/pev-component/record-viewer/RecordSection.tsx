import * as React from 'react';
import { IRecordSection } from '../../pev-interface/IRecordDataR';
import { Box } from '@mui/material';
import RecordElement from './RecordElement';

type RecordSectionProps = {
    section: IRecordSection;
};

const RecordSection = (props: RecordSectionProps) => {
    return (
        <Box sx={{ position: 'relative' }} width={`${props.section.width}px`} height={`${props.section.height}px`}>
            {props.section.items.map((item, idx) => {
                return <RecordElement key={idx} item={item} />;
            })}
        </Box>
    );
};

export default RecordSection;
