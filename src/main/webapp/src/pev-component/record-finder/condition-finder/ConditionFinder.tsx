import * as React from 'react';
import { Backdrop, Box, CircularProgress, Divider } from '@mui/material';
import ConditionFinderPanel from './ConditionFinderPanel';
import RecordGrid from '../RecordGrid';
import { ISearchCondition, ISearchConditionKeyValue } from '../../../pev-interface/IRecord';
import dayjs from 'dayjs';
import { TPactTpCd } from '../../../pev-type/TPactTpCd';
import { TDept } from '../../../pev-type/TDept';
import { TWriter } from '../../../pev-type/TWriter';
import { useGetRecordListMutation, useGetRecordListRequestQuery } from '../../../pev-service/RecordService';
import { setTargetRecords } from '../../../store/pev-slices/record';
import { setAlert } from '../../../store/pev-slices/environment';

type ConditionFinderProps = {
    currentIrb: string | null;
    currentRid: string | null;
};

const ConditionFinder = (props: ConditionFinderProps) => {
    const initialSearchCondition: ISearchCondition = {
        searchTargets: ['D001', 'D002', 'D003', 'D004'],
        searchFromDate: dayjs().add(-1, 'month').format('YYYY-MM-DD'),
        searchToDate: dayjs().add(-1, 'day').format('YYYY-MM-DD'),
        pactTpCd: TPactTpCd.ALL,
        deptType: TDept.ALL,
        deptCd: null,
        writerType: TWriter.ALL
    };

    const [searchCondition, setSearchCondition] = React.useState<ISearchCondition>(initialSearchCondition);

    const { data: searchConditionInSession, isLoading: searchConditionLoading } = useGetRecordListRequestQuery();

    React.useEffect(() => {
        if (searchConditionInSession) {
            setSearchCondition({
                searchTargets: searchConditionInSession.searchTargets,
                searchFromDate: searchConditionInSession.searchFromDate,
                searchToDate: searchConditionInSession.searchToDate,
                pactTpCd: searchConditionInSession.pactTpCd,
                deptType: searchConditionInSession.deptType,
                deptCd: searchConditionInSession.deptCd,
                writerType: searchConditionInSession.writerType
            });
        }
    }, [searchConditionInSession]);

    const handleSearchConditionChange = (conditions: ISearchConditionKeyValue[]) => {
        let nextSearchCondition = { ...searchCondition };

        conditions.forEach((condition) => {
            nextSearchCondition = { ...nextSearchCondition, [condition.key]: condition.value };
        });

        setSearchCondition(nextSearchCondition);
    };

    const [getRecordList, { data: recordList, isLoading: isRecordListLoading, reset: resetGetRecordList }] = useGetRecordListMutation();

    const handleListSearch = () => {
        getRecordList(searchCondition)
            .unwrap()
            .then((data) => {
                if (data.length === 0) {
                    setAlert({
                        type: 'warning',
                        message: '설정한 조건에 따른 기록지가 존재하지 않습니다.'
                    });
                }
            });
    };

    React.useEffect(() => {
        resetGetRecordList();
        setTargetRecords([]);
    }, [props.currentIrb, props.currentRid]);

    return (
        <Box sx={{ width: '100%', height: 'calc(100% - 64px)' }}>
            <Backdrop
                sx={{ color: '#fff', zIndex: (theme) => theme.zIndex.drawer + 1 }}
                open={isRecordListLoading || searchConditionLoading}
            >
                <CircularProgress color="inherit" />
            </Backdrop>
            {/*조건 설정 패널*/}
            <ConditionFinderPanel
                searchCondition={searchCondition}
                onSearchConditionChange={handleSearchConditionChange}
                onListSearch={handleListSearch}
            />
            <Divider sx={{ my: 1 }} />
            {/*목록 조회 그리드*/}
            <Box width={'100%'} height={'calc(100% - 264px)'}>
                <RecordGrid recordList={recordList || []} />
            </Box>
        </Box>
    );
};

export default ConditionFinder;
