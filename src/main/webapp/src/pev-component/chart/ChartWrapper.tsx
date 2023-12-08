import * as React from 'react';
import { IRecord } from '../../pev-interface/IRecord';
import SkeletonChart from './SkeletonChart';
import { useGetChartMutation } from '../../pev-service/RecordService';
import Chart from './Chart';
import { IChartP } from '../../pev-interface/IChart';

type ChartWrapperProps = {
    maskingYn: 'Y' | 'N';
    targetRecord: IRecord;
    onChartLoadingChange?: (isLoading: boolean) => void;
};

const ChartWrapper = (props: ChartWrapperProps) => {
    const [getChart, { data: chart, isLoading: isChartLoading }] = useGetChartMutation();

    React.useEffect(() => {
        const payload: IChartP = {
            maskingYn: props.maskingYn,
            record: props.targetRecord
        };

        getChart(payload);
    }, [props.targetRecord]);

    React.useEffect(() => {
        if (props.onChartLoadingChange) props.onChartLoadingChange(isChartLoading);
    }, [isChartLoading]);

    return (
        <React.Fragment>
            {isChartLoading && <SkeletonChart />}
            {!isChartLoading && chart && (
                <React.Fragment>
                    <Chart chart={chart} />
                </React.Fragment>
            )}
        </React.Fragment>
    );
};

export default ChartWrapper;
