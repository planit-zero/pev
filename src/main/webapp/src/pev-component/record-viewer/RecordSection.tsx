import * as React from 'react';
import { IRecordSection } from '../../pev-interface/IRecord';
import { Box } from '@mui/material';
import RecordEntity from './RecordEntity';

type RecordSectionProps = {
    section: IRecordSection;
};

const RecordSection = (props: RecordSectionProps) => {
    return (
        <Box sx={{ mb: 2 }}>
            {props.section.entities.map((entity, idx) => {
                return <RecordEntity key={idx} entity={entity} />;
            })}
        </Box>
    );
};

export default RecordSection;
