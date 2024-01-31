import * as React from 'react';
import { IRecord } from '../../pev-interface/IRecord';
import SkeletonChart from './SkeletonChart';
import { useGetChartMutation, useGetChartReplyMutation } from '../../pev-service/RecordService';
import Chart from './Chart';
import { IChartP, IChartReplyP, IChartReportValue } from '../../pev-interface/IChart';
import { ChartWrapperType } from '../../pev-type/TChart';
import { Box } from '@mui/material';
import ChartError from './ChartError';

type ChartWrapperProps = {
    mode: ChartWrapperType;
    maskingYn: 'Y' | 'N';
    targetRecord: IRecord;
    onChartLoadingChange?: (isLoading: boolean) => void;
    onChartErrorChange?: (isError: boolean) => void;
    reportValues?: IChartReportValue[];
    onValueChange?: (value: IChartReportValue) => void;
};

const ChartWrapper = (props: ChartWrapperProps) => {
    const [getChart, { data: chart, isLoading: isChartLoading, isError: isChartError }] = useGetChartMutation();
    const [getChartReply, { data: chartReply, isLoading: isChartReplyLoading }] = useGetChartReplyMutation();

    React.useEffect(() => {
        const payload: IChartP = {
            maskingYn: props.maskingYn,
            record: props.targetRecord
        };

        getChart(payload).then(() => {
            if (props.targetRecord.recordDetailType === 'D007') {
                const replyPayload: IChartReplyP = {
                    maskingYn: props.maskingYn,
                    mdrcId: props.targetRecord.mdrcId,
                    mdrcFomSeq: props.targetRecord.mdrcFomSeq
                };

                getChartReply(replyPayload);
            }
        });
    }, [props.targetRecord]);

    React.useEffect(() => {
        if (props.onChartLoadingChange) props.onChartLoadingChange(isChartLoading);
    }, [isChartLoading]);

    React.useEffect(() => {
        if (props.onChartErrorChange) props.onChartErrorChange(isChartError);
    });

    return (
        <React.Fragment>
            {isChartLoading && <SkeletonChart />}
            {!isChartLoading && !isChartError && chart && (
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
            {!isChartLoading && isChartError && <ChartError targetRecord={props.targetRecord} />}
            {props.targetRecord.recordDetailType === 'D007' && isChartReplyLoading && <SkeletonChart />}
            {props.targetRecord.recordDetailType === 'D007' &&
                !isChartReplyLoading &&
                !isChartError &&
                chartReply &&
                chartReply.replyYn === 'Y' && (
                    <React.Fragment>
                        <Box sx={{ my: 4 }} />
                        <Chart
                            mode={props.mode}
                            record={chartReply.record}
                            chart={chartReply.chart}
                            reportValues={props.reportValues}
                            onValueChange={props.onValueChange}
                        />
                    </React.Fragment>
                )}
        </React.Fragment>
    );
};

export default ChartWrapper;
