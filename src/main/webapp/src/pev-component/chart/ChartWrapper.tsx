import * as React from 'react';
import { IRecord } from '../../pev-interface/IRecord';
import SkeletonChart from './SkeletonChart';
import { useGetChartMutation, useGetChartReplyMutation, useGetFunctionChartMutation } from '../../pev-service/RecordService';
import Chart from './Chart';
import { IChart, IChartP, IChartReplyP, IChartReplyR, IChartReportValue } from '../../pev-interface/IChart';
import { ChartWrapperType } from '../../pev-type/TChart';
import {Box, Button} from '@mui/material';
import ChartError from './ChartError';
import SettingsOverscanIcon from '@mui/icons-material/SettingsOverscan';
import {Popup} from 'devextreme-react';

type ChartWrapperProps = {
    mode: ChartWrapperType;
    maskingYn: 'Y' | 'N';
    targetRecord: IRecord;
    onChartLoadingChange?: (isLoading: boolean) => void;
    onChartErrorChange?: (isError: boolean) => void;
    reportValues?: IChartReportValue[];
    onValueChange?: (value: IChartReportValue) => void;
    onFulfilledTimeStampChange?: (fulfilledTimeStamp: number | undefined) => void;
    searchTimeStamp?: number | null;
    searchText?: string | null;
    setSearchTarget?: (value: boolean) => void;
};

const ChartWrapper = (props: ChartWrapperProps) => {
    const [getChart, { isLoading: isChartLoading, isError: isChartError, fulfilledTimeStamp }] = useGetChartMutation();
    const [getChartReply, { isLoading: isChartReplyLoading }] = useGetChartReplyMutation();
    const [getFunctionChart, { isLoading: isFunctionChartLoading, isError: isFunctionChartError, fulfilledTimeStamp: functionTimeStamp }] =
        useGetFunctionChartMutation();

    const [orgChart, setOrgChart] = React.useState<IChart | null>(null);
    const [chart, setChart] = React.useState<IChart | null>(null);

    const [orgChartReply, setOrgChartReply] = React.useState<IChartReplyR | null>(null);
    const [chartReply, setChartReply] = React.useState<IChartReplyR | null>(null);

    const [orgFunctionCharts, setOrgFunctionCharts] = React.useState<IChart[] | null>(null);
    const [functionCharts, setFunctionCharts] = React.useState<IChart[] | null>(null);

    const [openModal, setOpenModal] = React.useState<boolean>(false);

    React.useEffect(() => {
        const payload: IChartP = {
            maskingYn: props.maskingYn,
            record: props.targetRecord
        };

        if (props.targetRecord.recordDetailType === 'EX_FUNCTION') {
            getFunctionChart(payload)
                .unwrap()
                .then((fc) => {
                    setFunctionChartToReplaced(fc);
                });
        } else {
            getChart(payload)
                .unwrap()
                .then((c) => {
                    setChartToReplaced(c);

                    if (props.targetRecord.recordDetailType === 'D007') {
                        const replyPayload: IChartReplyP = {
                            maskingYn: props.maskingYn,
                            mdrcId: props.targetRecord.mdrcId,
                            mdrcFomSeq: props.targetRecord.mdrcFomSeq
                        };

                        getChartReply(replyPayload)
                            .unwrap()
                            .then((cr) => {
                                setChartReplyToReplaced(cr);
                            });
                    }
                });
        }
    }, [props.targetRecord]);

    React.useEffect(() => {
        if (props.onChartLoadingChange) props.onChartLoadingChange(isChartLoading);
    }, [isChartLoading]);

    React.useEffect(() => {
        if (props.onChartErrorChange) props.onChartErrorChange(isChartError);
    }, [isChartError]);

    React.useEffect(() => {
        if (props.onFulfilledTimeStampChange) props.onFulfilledTimeStampChange(fulfilledTimeStamp);
    }, [fulfilledTimeStamp]);

    React.useEffect(() => {
        if (props.onFulfilledTimeStampChange) props.onFulfilledTimeStampChange(functionTimeStamp);
    }, [functionTimeStamp]);

    const replaceContentToSearchText = (element: any) => {
        const nextElement = { ...element };

        if (nextElement.content) {
            const startTagPattern = new RegExp('<mark>', 'gi');
            const endTagPattern = new RegExp('</mark>', 'gi');

            nextElement.content = nextElement.content.replace(startTagPattern, '');
            nextElement.content = nextElement.content.replace(endTagPattern, '');

            if (props.searchText && props.searchText !== '') {
                const pattern = new RegExp(`(${props.searchText})+(?!<*>)`, 'gi');

                let result;

                if (
                    nextElement.controlType === 'RADIO_BUTTON' ||
                    nextElement.controlType === 'CHECK_BOX' ||
                    nextElement.controlType === 'IMAGE'
                ) {
                    result = nextElement.content;
                } else {
                    result = nextElement.content.replace(pattern, `<mark>${props.searchText}</mark>`);
                }

                if (result !== '' && nextElement.content !== result) {
                    setTimeout(() => {
                        if (props.setSearchTarget) props.setSearchTarget(true);
                    }, 300);

                    nextElement.content = result;
                }
            }
        }

        if ('attributes' in nextElement) {
            nextElement.attributes = nextElement.attributes.map((a: any) => replaceContentToSearchText(a));
        }

        if ('values' in nextElement) {
            nextElement.values = nextElement.values.map((v: any) => replaceContentToSearchText(v));
        }

        return nextElement;
    };

    const setChartToReplaced = (oc: IChart | null) => {
        if (!oc) return;

        setOrgChart(oc);
        setChart({
            ...oc,
            sections: oc.sections.map((s) => {
                return {
                    ...s,
                    entities: s.entities.map((e) => {
                        return replaceContentToSearchText(e);
                    })
                };
            })
        });
    };

    const setChartReplyToReplaced = (oc: IChartReplyR | null) => {
        if (!oc) return;

        setOrgChartReply(oc);
        setChartReply({
            ...oc,
            chart: {
                ...oc.chart,
                sections: oc.chart.sections.map((s) => {
                    return {
                        ...s,
                        entities: s.entities.map((e) => {
                            return replaceContentToSearchText(e);
                        })
                    };
                })
            }
        });
    };

    const setFunctionChartToReplaced = (oc: IChart[] | null) => {
        if (!oc) return;

        setOrgFunctionCharts(oc);

        const nextFunctionCharts = oc.map((fc) => {
            return {
                ...fc,
                sections: fc.sections.map((s) => {
                    return {
                        ...s,
                        entities: s.entities.map((e) => {
                            return replaceContentToSearchText(e);
                        })
                    };
                })
            };
        });

        setFunctionCharts(nextFunctionCharts);
    };

    const handlePopupHidden = React.useCallback(() => {
        setOpenModal(false);
    }, [setOpenModal]);

    React.useEffect(() => {
        if (props.targetRecord.recordDetailType === 'D007') {
            if (!chartReply) return;
            if (props.searchTimeStamp) setChartReplyToReplaced(orgChartReply);
            if (!props.searchTimeStamp) setChartReply(orgChartReply);
        }

        if (props.targetRecord.recordDetailType === 'EX_FUNCTION') {
            if (!functionCharts) return;
            if (props.searchTimeStamp) setFunctionChartToReplaced(orgFunctionCharts);
            if (!props.searchTimeStamp) setFunctionCharts(orgFunctionCharts);
        } else {
            if (!chart) return;
            if (props.searchTimeStamp) setChartToReplaced(orgChart);
            if (!props.searchTimeStamp) setChart(orgChart);
        }
    }, [props.searchTimeStamp]);

    const getObservationChart = () => {
        return (
            <Chart
                mode={props.mode}
                maskingYn={props.maskingYn}
                record={props.targetRecord}
                chart={chart}
                reportValues={props.reportValues}
                onValueChange={props.onValueChange}
                index={0}
                openModal={openModal}
            />
        );
    };

    const isObservationChart = () => {
        if (props.targetRecord.recordDetailType !== 'EX_FUNCTION' && !isChartLoading && !isChartError && chart) {
            if (props.targetRecord.recordDetailType === 'NR_OBSERVATION') {
                return true;
            }
        }

        return false;
    }

    return (
        <React.Fragment>
            {((props.targetRecord.recordDetailType !== 'EX_FUNCTION' && isChartLoading) ||
                (props.targetRecord.recordDetailType === 'EX_FUNCTION' && isFunctionChartLoading)) && <SkeletonChart />}
            {props.targetRecord.recordDetailType === 'EX_FUNCTION' &&
                !isFunctionChartLoading &&
                !isFunctionChartError &&
                functionCharts &&
                functionCharts.map((c, idx) => {
                    return (
                        <React.Fragment key={idx}>
                            <Chart
                                mode={props.mode}
                                maskingYn={props.maskingYn}
                                record={props.targetRecord}
                                chart={c}
                                reportValues={props.reportValues}
                                onValueChange={props.onValueChange}
                                index={idx}
                                openModal={false}
                            />
                        </React.Fragment>
                    );
                })}
            {!isObservationChart() && (
                <React.Fragment>
                    <Chart
                        mode={props.mode}
                        maskingYn={props.maskingYn}
                        record={props.targetRecord}
                        chart={chart}
                        reportValues={props.reportValues}
                        onValueChange={props.onValueChange}
                        index={0}
                        openModal={false}
                    />
                </React.Fragment>
            )}
            {isObservationChart() && (
                <React.Fragment>
                    <Box sx={{display: 'flex', justifyContent: 'end'}}>
                        <Button variant="outlined" startIcon={<SettingsOverscanIcon />} onClick={() => setOpenModal(true)}>
                            확대
                        </Button>
                    </Box>
                    {getObservationChart()}
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
                            maskingYn={props.maskingYn}
                            record={chartReply.record}
                            chart={chartReply.chart}
                            reportValues={props.reportValues}
                            onValueChange={props.onValueChange}
                            index={0}
                            openModal={false}
                        />
                    </React.Fragment>
            )}
            <Popup
                showTitle={true}
                title={'임상관찰기록'}
                dragEnabled={true}
                hideOnOutsideClick={true}
                visible={openModal}
                onHiding={handlePopupHidden}
                contentRender={getObservationChart}
                showCloseButton={true}
                width={'85vw'}
                height={'85vh'}
            />
        </React.Fragment>
    );
};

export default ChartWrapper;
