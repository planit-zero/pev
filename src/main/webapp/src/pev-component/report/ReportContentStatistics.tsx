import * as React from 'react';
import { Card, Paper } from '@mui/material';
import {
    useGetStatisticsUserWeekListQuery,
    useGetStatisticsUserHourListQuery,
    useGetEventSearchTargetDistributionListQuery
} from '../../pev-service/ReportService';
import Chart, { ArgumentAxis, Export, Format, Label, Legend, Series, Tooltip } from 'devextreme-react/chart';
import { PieChart } from 'devextreme-react';
import { Connector } from 'devextreme-react/pie-chart';

type ReportContentStatisticsProps = {
    menuIndex: number;
};

const ReportContentStatistics = (props: ReportContentStatisticsProps) => {
    const { data: statisticsUserWeekList } = useGetStatisticsUserWeekListQuery();
    const { data: statisticsUserHourList } = useGetStatisticsUserHourListQuery();
    const { data: eventSearchTargetDistributionList } = useGetEventSearchTargetDistributionListQuery();

    const getStatisticsUserWeek = () => {
        if (props.menuIndex === 7) return userChart();
        if (props.menuIndex === 8) return searchChart();
    };

    const userChart = () => {
        return (
            <>
                <Card sx={{ marginBottom: '10vh' }}>
                    <Chart title={'주간 접속 분포'} dataSource={statisticsUserWeekList} height={'35vh'}>
                        <ArgumentAxis>
                            <Label format="decimal" />
                        </ArgumentAxis>
                        <Series argumentField={'weekFromMonday'} valueField={'count'} type={'spline'} color={'#3f51b5'} />
                        <Legend visible={false} />
                        <Export enabled={true} />
                        <Tooltip enabled={true} />
                    </Chart>
                </Card>
                <Card>
                    <Chart title={'접속 시간대'} dataSource={statisticsUserHourList} height={'35vh'}>
                        <ArgumentAxis tickInterval={1}>
                            <Label format="decimal" />
                        </ArgumentAxis>
                        <Series argumentField={'hour'} valueField={'count'} type={'bar'} color={'#3f51b5'} />
                        <Legend visible={false} />
                        <Export enabled={true} />
                        <Tooltip enabled={true} />
                    </Chart>
                </Card>
            </>
        );
    };

    const customizeTooltip = (arg: { argument: string; percent: number }) => {
        return {
            text: `${arg.argument} - ${(arg.percent * 100).toFixed(2)}%`
        };
    };

    const customizeText = (arg: any) => {
        return arg.argument;
    };

    const searchChart = () => {
        return (
            <>
                <Card>
                    <PieChart
                        type={'doughnut'}
                        title={'기록유형 분포'}
                        palette={'Soft Pastel'}
                        dataSource={eventSearchTargetDistributionList}
                        height={'80vh'}
                    >
                        <Legend
                            orientation={'horizontal'}
                            itemTextPosition={'right'}
                            horizontalAlignment={'center'}
                            verticalAlignment={'bottom'}
                            columnCount={4}
                        />
                        <Series argumentField="searchTargets" valueField={'count'}>
                            <Label visible={true} format="fixedPoint" customizeText={customizeText}>
                                <Connector visible={true} />
                            </Label>
                        </Series>
                        <Export enabled={true} />
                        <Tooltip enabled={true} customizeTooltip={customizeTooltip}>
                            <Format type={'millions'} />
                        </Tooltip>
                    </PieChart>
                </Card>
            </>
        );
    };

    return <Paper sx={{ width: '40%', height: 'calc(100% - 75px)', p: '20px', ml: '15px' }}>{getStatisticsUserWeek()}</Paper>;
};

export default ReportContentStatistics;
