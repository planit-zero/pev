import * as React from 'react';
import { useSelector } from '../../store';
import ChartBox from './ChartBox';

const ChartContainer = () => {
    const { targetRecords } = useSelector((state) => state.record);

    // React.useEffect(() => {
    //     if (targetRecords.length > 1) {
    //         const element = document.getElementById(`target-record-${targetRecords.length - 1}`);
    //
    //         if (element) {
    //             element.scrollIntoView({ block: 'start', inline: 'nearest', behavior: 'smooth' });
    //         }
    //     }
    // }, [targetRecords]);

    return (
        <React.Fragment>
            {targetRecords.map((targetRecord, idx) => {
                return <ChartBox key={JSON.stringify(targetRecord)} index={idx} targetRecord={targetRecord} />;
            })}
        </React.Fragment>
    );
};

export default React.memo(ChartContainer);
