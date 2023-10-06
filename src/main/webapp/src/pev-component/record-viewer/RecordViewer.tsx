import * as React from 'react';
import { useSelector } from '../../store';
import { Backdrop, Box, CircularProgress } from '@mui/material';
import { useGetFormContentMutation } from '../../pev-service/FormService';
import { IFormContentP } from '../../pev-interface/IForm';
import FormSheet from './form/FormSheet';
import RecordSheet from './RecordSheet';
import dayjs from 'dayjs';

type RecordViewerProps = {};

const RecordViewer = (props: RecordViewerProps) => {
    const { targetRecords } = useSelector((state) => state.record);

    return (
        <Box display={'flex'} flexDirection={'column'} alignItems={'center'}>
            {targetRecords.map((target, idx) => {
                return <RecordSheet key={`${dayjs()}-${idx}`} targetRecord={target} />;
            })}
        </Box>
    );
};

export default RecordViewer;
