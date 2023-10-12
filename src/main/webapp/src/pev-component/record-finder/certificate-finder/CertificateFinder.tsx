import * as React from 'react';
import { Backdrop, Box, CircularProgress } from '@mui/material';
import { ISearchCondition } from '../../../pev-interface/IRecord';
import dayjs from 'dayjs';
import { TPactTpCd } from '../../../pev-type/TPactTpCd';
import { TDept } from '../../../pev-type/TDept';
import { TWriter } from '../../../pev-type/TWriter';
import { useGetRecordListMutation } from '../../../pev-service/RecordService';
import RecordGrid from '../RecordGrid';

const CertificateFinder = () => {
    const condition: ISearchCondition = {
        searchTargets: ['D009', 'D035'],
        searchFromDate: dayjs('1998-01-01').format('YYYY-MM-DD'),
        searchToDate: dayjs().add(-1, 'day').format('YYYY-MM-DD'),
        pactTpCd: TPactTpCd.ALL,
        deptType: TDept.ALL,
        deptCd: null,
        writerType: TWriter.ALL
    };

    const [getRecordList, { data: recordList, isLoading: isRecordListLoading }] = useGetRecordListMutation();

    React.useEffect(() => {
        getRecordList(condition);
    }, []);

    return (
        <Box sx={{ p: 1, width: '100%', height: 'calc(100% - 60px)' }}>
            <Backdrop sx={{ color: '#fff', zIndex: (theme) => theme.zIndex.drawer + 1 }} open={isRecordListLoading}>
                <CircularProgress color="inherit" />
            </Backdrop>
            <RecordGrid recordList={recordList || []} />
        </Box>
    );
};

export default CertificateFinder;
