import * as React from 'react';
import { Box } from '@mui/material';
import ConditionFinderPanel from './ConditionFinderPanel';
import ConditionFinderGrid from './ConditionFinderGrid';
import { ISearchCondition } from '../../../pev-interface/IRecord';
import { TRecord } from '../../../pev-type/TRecord';
import dayjs from 'dayjs';
import { TPactTpCd } from '../../../pev-type/TPactTpCd';

const ConditionFinder = () => {
    const initialSearchCondition: ISearchCondition = {
        searchTargets: [TRecord.DR],
        searchFromDate: dayjs().add(-1, 'month').format('YYYY-MM-DD'),
        searchToDate: dayjs().add(-1, 'day').format('YYYY-MM-DD'),
        pactTpCd: TPactTpCd.ALL,
        deptType: 'ALL',
        deptCd: null,
        writerType: 'ALL'
    };

    const [searchCondition, setSearchCondition] = React.useState<ISearchCondition>(initialSearchCondition);

    const handleSearchConditionChange = (key: string, value: string | null) => {
        setSearchCondition({
            ...searchCondition,
            [key]: value
        });
    };

    return (
        <Box sx={{ p: 1, width: '100%', height: 'calc(100% - 60px)' }}>
            {/*조건 설정 패널*/}
            <ConditionFinderPanel searchCondition={searchCondition} onSearchConditionChange={handleSearchConditionChange} />
            {/*목록 조회 그리드*/}
            <ConditionFinderGrid />
        </Box>
    );
};

export default ConditionFinder;
