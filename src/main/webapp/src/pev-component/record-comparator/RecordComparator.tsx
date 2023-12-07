import * as React from 'react';
import { Box } from '@mui/material';
import ChartWrapper from '../chart/ChartWrapper';
import { IRecord } from '../../pev-interface/IRecord';

type RecordComparatorProps = {
    targetRecord: IRecord;
};

const RecordComparator = (props: RecordComparatorProps) => {
    return (
        <Box
            sx={{
                position: 'absolute',
                top: '50%',
                left: '50%',
                transform: 'translate(-50%, -50%)',
                width: 'fit-content',
                maxWidth: '90vw',
                height: 'fit-content',
                maxHeight: '90vh',
                backgroundColor: '#eef2f6',
                boxShadow: 24,
                p: 2,
                display: 'flex',
                gap: 2,
                overflowY: 'scroll'
            }}
        >
            <Box sx={{ minWidth: 600, bgcolor: 'background.paper', p: 2 }}>
                <ChartWrapper maskingYn={'N'} targetRecord={props.targetRecord} />
            </Box>
            <Box sx={{ minWidth: 600, bgcolor: 'background.paper', p: 2 }}>
                <ChartWrapper maskingYn={'Y'} targetRecord={props.targetRecord} />
            </Box>
        </Box>
    );
};

export default RecordComparator;
