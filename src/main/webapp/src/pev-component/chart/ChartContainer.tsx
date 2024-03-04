import * as React from 'react';
import { useSelector } from '../../store';
import ChartBox from './ChartBox';
import { IRecordDetailR } from '../../pev-interface/IRecordDetail';
import ChartFilter from './ChartFilter';

interface IRecordStatus {
    index: number;
    isCompleted: boolean;
}

const ChartContainer = () => {
    const { targetRecords } = useSelector((state) => state.record);

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
                if (index === idx) return { ...s, isCompleted: true };
                return s;
            });

            setStatusList(nextStatusList);
        }
    };

    const [searchTimeStamp, setSearchTimeStamp] = React.useState<number | null>(null);
    const [searchText, setSearchText] = React.useState<string | null>(null);

    const handleSearchTextChange = (value: string | null) => {
        let nextValue: string | null = null;

        if (value) {
            nextValue = value.replace(/[`~!@#$%^&*()_|+\-=?;:'",.<>{}\[\]\\\/]/gi, '');
        }

        setSearchText(nextValue);
    };

    const handleSearchTextApply = () => {
        setSearchTimeStamp(new Date().getTime());
    };

    const handleSearchTextClear = () => {
        setSearchTimeStamp(null);
        setSearchText(null);
    };

    const [searchTargetCount] = React.useState<number>(0);

    const ChartBoxWrapper = (targetRecord: IRecordDetailR, idx: number) => {
        if (idx !== 0) {
            const prevStatus = statusList.find((s) => s.index === idx - 1);
            if (!prevStatus) return null;
            if (!prevStatus.isCompleted) return null;
        }

        return (
            <ChartBox
                key={JSON.stringify(targetRecord)}
                index={idx}
                targetRecord={targetRecord}
                onStatusChange={handleStatusChange}
                searchTimeStamp={searchTimeStamp}
                searchText={searchText}
            />
        );
    };

    return (
        <React.Fragment>
            {statusList.length > 0 && (
                <ChartFilter
                    allCount={statusList.length}
                    loadedCount={statusList.filter((s) => s.isCompleted).length}
                    searchTargetCount={searchTargetCount}
                    searchText={searchText}
                    onSearchTextChange={handleSearchTextChange}
                    onSearchTextApply={handleSearchTextApply}
                    onSearchTextClear={handleSearchTextClear}
                />
            )}
            {targetRecords.map((targetRecord, idx) => {
                return ChartBoxWrapper(targetRecord, idx);
            })}
        </React.Fragment>
    );
};

export default React.memo(ChartContainer);
