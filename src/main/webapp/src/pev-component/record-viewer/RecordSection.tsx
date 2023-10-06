import * as React from 'react';
import { IRecordSection } from '../../pev-interface/IRecord';
import { Box } from '@mui/material';
import RecordEntity from './RecordEntity';

type RecordSectionProps = {
    type: string;
    section: IRecordSection;
};

const RecordSection = (props: RecordSectionProps) => {
    return (
        <Box display={'flex'} flexDirection={'column'} gap={2}>
            {props.section.entities.map((entity, idx) => {
                return <RecordEntity key={idx} sectionType={props.type} entity={entity} />;
            })}
        </Box>
    );
};

export default RecordSection;
