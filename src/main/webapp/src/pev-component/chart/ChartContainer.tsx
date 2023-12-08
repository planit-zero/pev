import * as React from 'react';
import { useSelector } from '../../store';
import ChartBox from './ChartBox';

const ChartContainer = () => {
    const { targetRecords } = useSelector((state) => state.record);

    return (
        <React.Fragment>
            {targetRecords.map((targetRecord, idx) => {
                return <ChartBox key={idx} targetRecord={targetRecord} />;
            })}
        </React.Fragment>
    );
};

export default React.memo(ChartContainer);
