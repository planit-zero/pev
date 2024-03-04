import * as React from 'react';
import { IRecord } from '../../pev-interface/IRecord';
import ChartToolbar from './ChartToolbar';
import ChartWrapper from './ChartWrapper';
import { Paper } from '@mui/material';

type ChartBoxProps = {
    index: number;
    targetRecord: IRecord;
    onStatusChange: (index: number, fulfilledTimeStamp: number | undefined) => void;
    searchTimeStamp: number | null;
    searchText: string | null;
};

const ChartBox = (props: ChartBoxProps) => {
    const [isChartLoading, setChartLoading] = React.useState<boolean>(true);
    const [isChartError, setChartError] = React.useState<boolean>(false);

    const handleChartLoadingChange = (isLoading: boolean) => {
        setChartLoading(isLoading);
    };

    const handleChartErrorChange = (isError: boolean) => {
        setChartError(isError);
    };

    const handleChartFulfilledTimeStampChange = (fulfilledTimeStamp: number | undefined) => {
        props.onStatusChange(props.index, fulfilledTimeStamp);
    };

    const [isSearchTarget, setSearchTarget] = React.useState<boolean>(false);

    React.useEffect(() => {
        setSearchTarget(false);
    }, [props.searchTimeStamp]);

    return (
        <Paper
            id={`target-record-${props.index}`}
            sx={{
                minWidth: 600,
                p: 2,
                mb: 2,
                borderRadius: 0,
                display: !props.searchTimeStamp || (props.searchTimeStamp && isSearchTarget) ? 'block' : 'none'
            }}
        >
            <ChartToolbar targetRecord={props.targetRecord} isChartLoading={isChartLoading} isChartError={isChartError} />
            <ChartWrapper
                mode={'NORMAL'}
                maskingYn={'Y'}
                targetRecord={props.targetRecord}
                onChartLoadingChange={handleChartLoadingChange}
                onChartErrorChange={handleChartErrorChange}
                onFulfilledTimeStampChange={handleChartFulfilledTimeStampChange}
                searchTimeStamp={props.searchTimeStamp}
                searchText={props.searchText}
                setSearchTarget={setSearchTarget}
            />
        </Paper>
    );
};

export default ChartBox;
