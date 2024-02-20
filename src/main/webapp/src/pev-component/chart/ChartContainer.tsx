import * as React from 'react';
import { useSelector } from '../../store';
import ChartBox from './ChartBox';
import { IRecordDetailR } from '../../pev-interface/IRecordDetail';

interface IRecordStatus {
    index: number;
    isCompleted: boolean;
}

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

    const [statusList, setStatusList] = React.useState<IRecordStatus[]>([]);

    React.useEffect(() => {
        const nextStatusList: IRecordStatus[] = [...targetRecords].map((t, idx) => {
            return {
                index: idx,
                isCompleted: statusList.find((s) => idx === s.index)?.isCompleted || false
            };
        });

        setStatusList(nextStatusList);
    }, [targetRecords]);

    const handleStatusChange = (index: number, fulfilledTimeStamp: number | undefined) => {
        if (fulfilledTimeStamp) {
            const nextStatusList = [...statusList].map((s, idx) => {
                if (index === idx) return { index: s.index, isCompleted: true };
                return s;
            });

            setStatusList(nextStatusList);
        }
    };

    React.useEffect(() => {
        console.log(statusList);
    }, [statusList]);

    const ChartBoxWrapper = (targetRecord: IRecordDetailR, idx: number) => {
        if (idx !== 0) {
            const status = statusList.find((s) => s.index === idx - 1);
            if (!status) return null;
            if (!status.isCompleted) return null;
        }

        return <ChartBox key={JSON.stringify(targetRecord)} index={idx} targetRecord={targetRecord} onStatusChange={handleStatusChange} />;
    };

    return (
        <React.Fragment>
            {targetRecords.map((targetRecord, idx) => {
                return ChartBoxWrapper(targetRecord, idx);
            })}
        </React.Fragment>
    );
};

export default React.memo(ChartContainer);
