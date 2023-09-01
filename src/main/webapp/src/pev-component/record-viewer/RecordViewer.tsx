import * as React from 'react';
import { useSelector } from '../../store';
import { Backdrop, Box, CircularProgress } from '@mui/material';
import RecordSheet from './RecordSheet';
import { useGetRecordDataListMutation } from '../../pev-service/RecordService';

type RecordViewerProps = {};

const RecordViewer = (props: RecordViewerProps) => {
    const { targetRecords } = useSelector((state) => state.record);

    const [getRecordDataList, { data: dataList, isLoading }] = useGetRecordDataListMutation();

    React.useEffect(() => {
        if (targetRecords.length > 0) {
            getRecordDataList({ targets: targetRecords });
        }
    }, [targetRecords]);

    return (
        <React.Fragment>
            <Box display={'flex'} flexDirection={'column'} alignItems={'center'}>
                {dataList &&
                    dataList.map((data, idx) => {
                        return <RecordSheet key={idx} data={data} />;
                    })}
            </Box>
            <Backdrop open={isLoading} sx={{ color: (theme) => theme.palette.primary.main, zIndex: (theme) => theme.zIndex.modal + 1 }}>
                <CircularProgress color={'inherit'} />
            </Backdrop>
        </React.Fragment>
    );
};

export default RecordViewer;
