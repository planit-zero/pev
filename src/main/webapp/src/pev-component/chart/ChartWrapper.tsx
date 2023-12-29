import * as React from 'react';
import { IRecord } from '../../pev-interface/IRecord';
import SkeletonChart from './SkeletonChart';
import { useGetChartMutation } from '../../pev-service/RecordService';
import Chart from './Chart';
import { IChartP, IChartReportValue } from '../../pev-interface/IChart';
import { ChartWrapperType } from '../../pev-type/TChart';

type ChartWrapperProps = {
    mode: ChartWrapperType;
    maskingYn: 'Y' | 'N';
    targetRecord: IRecord;
    onChartLoadingChange?: (isLoading: boolean) => void;
    reportValues?: IChartReportValue[];
    onValueChange?: (value: IChartReportValue) => void;
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
                    <Chart
                        mode={props.mode}
                        record={props.targetRecord}
                        chart={chart}
                        reportValues={props.reportValues}
                        onValueChange={props.onValueChange}
                    />
                </React.Fragment>
            )}
        </React.Fragment>
    );
};

export default ChartWrapper;
