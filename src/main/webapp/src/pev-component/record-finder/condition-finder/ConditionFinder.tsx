import * as React from 'react';
import { Box } from '@mui/material';
import ConditionFinderPanel from './ConditionFinderPanel';
import ConditionFinderGrid from './ConditionFinderGrid';
import { ISearchCondition, ISearchConditionKeyValue } from '../../../pev-interface/IRecord';
import { TRecord } from '../../../pev-type/TRecord';
import dayjs from 'dayjs';
import { TPactTpCd } from '../../../pev-type/TPactTpCd';
import { TDept } from '../../../pev-type/TDept';
import { TWriter } from '../../../pev-type/TWriter';

const ConditionFinder = () => {
    const initialSearchCondition: ISearchCondition = {
        searchTargets: [TRecord.DR, 'D001', 'D002', 'D003', 'D004', 'D031', 'D005', 'D006', 'D010', 'D011', 'D007', 'D020', 'D030'],
        searchFromDate: dayjs().add(-1, 'month').format('YYYY-MM-DD'),
        searchToDate: dayjs().add(-1, 'day').format('YYYY-MM-DD'),
        pactTpCd: TPactTpCd.ALL,
        deptType: TDept.ALL,
        deptCd: null,
        writerType: TWriter.ALL
    };

    const [searchCondition, setSearchCondition] = React.useState<ISearchCondition>(initialSearchCondition);

    const handleSearchConditionChange = (conditions: ISearchConditionKeyValue[]) => {
        let nextSearchCondition = { ...searchCondition };

        conditions.forEach((condition) => {
            nextSearchCondition = { ...nextSearchCondition, [condition.key]: condition.value };
        });

        setSearchCondition(nextSearchCondition);
    };

    React.useEffect(() => {
        console.log('searchCondition', searchCondition);
    }, [searchCondition]);

    return (
        <Box sx={{ p: 1, width: '100%', height: 'calc(100% - 60px)' }}>
            {/*조건 설정 패널*/}
            <ConditionFinderPanel searchCondition={searchCondition} onSearchConditionChange={handleSearchConditionChange} />
            {/*목록 조회 그리드*/}
            <ConditionFinderGrid searchCondition={searchCondition} />
        </Box>
    );
};

export default ConditionFinder;
